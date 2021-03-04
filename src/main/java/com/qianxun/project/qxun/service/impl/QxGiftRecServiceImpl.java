package com.qianxun.project.qxun.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAlias;


import com.qianxun.common.enums.CoinConstants;
import com.qianxun.common.exception.coin.AccountBalanceNotEnoughException;
import com.qianxun.common.exception.user.ParameterException;
import com.qianxun.common.utils.DateUtils;
import com.qianxun.common.utils.StringUtils;
import com.qianxun.project.coin.domain.CoinAccount;
import com.qianxun.project.coin.domain.vo.BalanceChange;
import com.qianxun.project.coin.service.ICoinAccountService;
import com.qianxun.project.qxun.domain.*;
import com.qianxun.project.qxun.domain.vo.BuyVo;
import com.qianxun.project.qxun.mapper.QxGiftRecMapper;
import com.qianxun.project.qxun.service.*;
import com.qianxun.project.system.domain.SysUser;
import com.qianxun.project.system.domain.vo.UserTeam;
import com.qianxun.project.system.mapper.UserTeamMapper;
import com.qianxun.project.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.sun.org.apache.regexp.internal.recompile;

@Service
public class QxGiftRecServiceImpl implements IQxGiftRecService {

    private static final Logger log = LoggerFactory.getLogger(QxGiftRecServiceImpl.class);

    @Autowired
    private QxGiftRecMapper qxGiftRecMapper;

    @Autowired
    private UserTeamMapper userTeamMapper;

    @Autowired
    private IQxGiftCfgService qxGiftCfgService;

    @Autowired
    private ICoinAccountService coinAccountService;

    @Autowired
    private IQxUserAccService qxUserAccService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private IQxLayerCfgService qxLayerCfgService;

    @Autowired
    private IQxShareRecService qxShareRecService;
    
    @Autowired
    private IQxVipCfgService qxVipCfgService;
    
    @Autowired
    private IQxVipRecService qxVipRecService;
    
    @Autowired
    private IQxLayerRecService qxLayerRecService;
    

    @Override
    public QxGiftRec selectQxGiftRecById(Long id) {
        return qxGiftRecMapper.selectQxGiftRecById(id);
    }

    @Override
    public List<QxGiftRec> selectQxGiftRecList(QxGiftRec qxGiftRec) {
        return qxGiftRecMapper.selectQxGiftRecList(qxGiftRec);
    }

    @Override
    public int insertQxGiftRec(QxGiftRec qxGiftRec) {
        qxGiftRec.setCreateTime(DateUtils.getNowDate());
        return qxGiftRecMapper.insertQxGiftRec(qxGiftRec);
    }

    @Override
    public int updateQxGiftRec(QxGiftRec qxGiftRec) {
        qxGiftRec.setUpdateTime(DateUtils.getNowDate());
        return qxGiftRecMapper.updateQxGiftRec(qxGiftRec);
    }

    @Override
    public int deleteQxGiftRecByIds(Long[] ids) {
        return qxGiftRecMapper.deleteQxGiftRecByIds(ids);
    }

    @Override
    public int deleteQxGiftRecById(Long id) {
        return qxGiftRecMapper.deleteQxGiftRecById(id);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void buy(long userId, BuyVo buyVo) {
        // 条件判断
        if (buyVo.getId() <= 0 || buyVo.getId() > 3 || buyVo.getNum() <= 0) {
            throw new ParameterException();
        }

        QxGiftCfg qxGiftCfg = qxGiftCfgService.selectQxGiftCfgById(buyVo.getId());

        CoinAccount coinAccount = coinAccountService.getAccountByUserIdAndCoinId(userId, 1L);
        BigDecimal totalPrice = new BigDecimal(buyVo.getNum()).multiply(qxGiftCfg.getGcNum());
        BigDecimal etNum = totalPrice.multiply(qxGiftCfg.getEtRatio()).setScale(2, BigDecimal.ROUND_HALF_UP);

        // 判断余额
        if (coinAccount.getAvailable().compareTo(totalPrice) < 0) {
            throw new AccountBalanceNotEnoughException();
        }

        // 操作用户资产
        List<BalanceChange> cList = new ArrayList<BalanceChange>();
        BalanceChange c = new BalanceChange();
        c.setUserId(userId);
        c.setAvailable(totalPrice.negate());
        c.setCoinId(1L);
        c.setCoinName("USDT");
        c.setIncomeType(CoinConstants.IncomeType.PAYOUT);
        c.setMainType(CoinConstants.MainType.USE);
        c.setSonType("buyGift");
        cList.add(c);

        BalanceChange c2 = new BalanceChange();
        c2.setUserId(userId);
        c2.setAvailable(etNum);
        c2.setCoinId(2L);
        c2.setCoinName("ET");
        c2.setIncomeType(CoinConstants.IncomeType.INCOME);
        c2.setMainType(CoinConstants.MainType.AIRDROP);
        c2.setSonType("buyGift");
        cList.add(c2);

        // 生成购买礼包记录
        QxGiftRec r = new QxGiftRec();
        r.setUserId(userId);
        r.setGiftLevel(qxGiftCfg.getId());
        r.setGcNum(totalPrice);
        r.setEtNum(etNum);
        insertQxGiftRec(r);

        log.info("用户[{}],购买[{}]份[{}]礼包,总共[{}]GC,获得[{}]ET", userId, buyVo.getNum(), qxGiftCfg.getGrade(), totalPrice,
                etNum);

        List<QxGiftCfg> cfgList = qxGiftCfgService.selectQxGiftCfgList(new QxGiftCfg());

//      1.2 升级逻辑
        QxUserAcc acc = qxUserAccService.selectQxUserAccByUserId(userId);
        if (StringUtils.isNull(acc)) {
            acc = new QxUserAcc();
            acc.setUserId(userId);
            acc.setGiftGrade(qxGiftCfg.getId());
            acc.setVipGrade(0L);// 默认
            qxUserAccService.insertQxUserAcc(acc);
        } else {
            //如用户礼包等级为3则不需要升级礼包等级了
            if ( acc.getGiftGrade()<3){
            //用户购买礼包的总花费
            BigDecimal totalGc = qxGiftRecMapper.selectQxGiftRecByUserIdGcTotal(userId);
            totalGc = totalGc.add(totalPrice);
            //降序排列
            Collections.reverse(cfgList);
            for (QxGiftCfg cfgL : cfgList) {
                // 总的充值数量和礼包数据进行对比
                if (cfgL.getGcNum().compareTo(totalGc) <= 0) {
                    if (acc.getGiftGrade().longValue()<cfgL.getId().longValue()) {
                        acc.setGiftGrade(cfgL.getId());
                        qxUserAccService.updateQxUserAcc(acc);
                    }
                    break;
                }
            }
            }
        }

//      1.3 层级分佣逻辑
        List<UserTeam> userList = userTeamMapper.selectUserTeamList();
        List<QxUserAcc> accList = qxUserAccService.selectQxUserAccList(new QxUserAcc());
        Map<Long, QxUserAcc> accMap = getAccMap(accList);
        List<QxLayerCfg> layerCfgList = qxLayerCfgService.selectQxLayerCfgList(new QxLayerCfg());
        Map<Long, QxLayerCfg> layerCfgMap = getLayerCfgMap(layerCfgList);

        SysUser user = sysUserService.selectUserById(userId);
        //查询到当前购买用户的父级
        Long parentId = user.getParentId();

        long i = 1;
        while (i <= 10 && StringUtils.isNotNull(parentId) && parentId.longValue() > 0) {
            // 当前父级的直推人数
            List<UserTeam> directList = directPushUser(parentId, userList);
            System.out.println(JSON.toJSONString(directList));

            // 有效直推人数
            Integer value = getUserDirect(parentId, directList, accMap);
            System.out.println(value);

            QxLayerCfg layerCfg = layerCfgMap.get(i);
           log.info("layerCfg============="+layerCfg);

            //判断直推人数满足那个层奖
            if (layerCfg.getCondition().intValue() <= value) {
                //发放奖励
                QxLayerRec layerRec = new QxLayerRec();
                layerRec.setEra((long) i);
                layerRec.setFromId(userId);
                layerRec.setUserId(parentId);
                layerRec.setNum(totalPrice.multiply(layerCfg.getRatio()).setScale(2, BigDecimal.ROUND_HALF_UP));
                qxLayerRecService.insertQxLayerRec(layerRec);
                BalanceChange c3 = new BalanceChange();
                c3.setUserId(parentId);
                c3.setAvailable(layerRec.getNum());
                c3.setCoinId(1L);
                c3.setCoinName("USDT");
                c3.setIncomeType(CoinConstants.IncomeType.INCOME);
                c3.setMainType(CoinConstants.MainType.AIRDROP);
                c3.setSonType("buyGiftLayer");
                cList.add(c3);
            }

            i = i + 1;
            user = sysUserService.selectUserById(parentId);
            parentId = user.getParentId();
        }

//      1.4 代数分享奖逻辑【无限代极差】

        // 向上递归判断代数分享等级+代数奖励
        // 低级分佣差价/平级+高级不分佣
        BigDecimal totalLayerRatio = new BigDecimal("0.2");// 所有代数分享奖满拨20%做极差

        Long userId_2 = userId;
        user = sysUserService.selectUserById(userId);
        //购买礼包用户上级
        Long parentId2 = user.getParentId();
        Long downGrade = 0L;// 下级等级
        Long upGrade = 0L;// 上级等级
        BigDecimal downRatio = new BigDecimal(0);// 下级分佣比例
        BigDecimal upRatio = new BigDecimal(0);// 上级分佣比例
        int j = 0;// 递归到的次数
        while (StringUtils.isNotNull(parentId2) && parentId2.longValue() > 0) {// 有上级
            //查看上级等级信息
            QxUserAcc parentAcc = qxUserAccService.selectQxUserAccByUserId(parentId2);
            //查看上级用户信息
            SysUser parentUser = sysUserService.selectUserById(parentId2);
            //如果上级的等级信息为空则表示用户尚未购买礼包
            if (StringUtils.isNull(parentAcc)) {
                //没有上级
                if (StringUtils.isNotNull(parentUser)) {
                    parentId2 = parentUser.getParentId();
                } else {
                    parentId2 = 0L;
                }
                continue;
            }
            // 现在的上级等级
            Long nowGrade = parentAcc.getGiftGrade();

            if (0 != nowGrade.longValue()) {// 购买过礼包
                BigDecimal nowShareRatio = getUserShareRatio(parentId2, userList, accMap, nowGrade);

                // 1.分佣记录
                QxShareRec shareRec = new QxShareRec();
                shareRec.setUserId(parentId2);
                shareRec.setFromId(userId);
                shareRec.setUserGrade(nowGrade);
                //当前用户获得分享奖励的比例
                shareRec.setRatio(nowShareRatio);

                j = j + 1;
                shareRec.setEra((long) j);
                if (j == 1) {// 第1次 直接计算分佣
                    downGrade = nowGrade;
                    downRatio = nowShareRatio;
                    shareRec.setNum(totalPrice.multiply(nowShareRatio).setScale(2, BigDecimal.ROUND_HALF_UP));// 分佣数量
                    shareRec.setActualRatio(nowShareRatio);// 实际分佣比例
                } else {// 超过2次
                    
                    if (totalLayerRatio.compareTo(nowShareRatio) == 0 && downRatio.compareTo(nowShareRatio)==0) {// 到达最高20%分佣
                        break;
                    }

                    // 如果 上级<下级 跳过该次分佣
                    if (downRatio.compareTo(nowShareRatio) > 0) {
                        log.debug("[{}]对比[{}]上级分佣比例[{}]<=下级分佣比例[{}],跳过代数分享分佣。", parentId, userId_2, nowShareRatio,
                                downRatio);
                        parentId2 = parentUser.getParentId();
                        continue;
                    } else if (downRatio.compareTo(nowShareRatio) == 0) {// 如果 上级=下级 平级则下级吃到分佣
                        log.debug("[{}]对比[{}]上级分佣比例[{}]==下级分佣比例[{}],平级则下级吃到分佣。", parentId, userId_2, nowShareRatio,
                                downRatio);
                    }

                    upGrade = nowGrade;// 当前变上级
                    upRatio = nowShareRatio;// 当前变上级
                    log.debug("对比分佣比例:downRatio[{}]-upRatio[{}]", downRatio, upRatio);
                    // 计算级差
                    BigDecimal shareRatio = nowShareRatio.subtract(downRatio);

                    shareRec.setNum(totalPrice.multiply(shareRatio).setScale(2, BigDecimal.ROUND_HALF_UP));// 扣完极差后的分佣数量
                    shareRec.setActualRatio(shareRatio);// 扣完极差后的分佣比例

                    downGrade = upGrade;// 上级变下级
                    downRatio = upRatio;// 上级变下级分佣

                }
                if ((shareRec.getRatio().compareTo(BigDecimal.ZERO) == 0) == false) {// 非平级
                    qxShareRecService.insertQxShareRec(shareRec);

                    BalanceChange c4 = new BalanceChange();
                    c4.setUserId(parentId2);
                    c4.setAvailable(shareRec.getNum());
                    c4.setCoinId(1L);
                    c4.setCoinName("USDT");
                    c4.setIncomeType(CoinConstants.IncomeType.INCOME);
                    c4.setMainType(CoinConstants.MainType.AIRDROP);
                    c4.setSonType("buyGiftShare");
                    cList.add(c4);
                }


            }

            parentId2 = parentUser.getParentId();
            userId_2 = parentAcc.getUserId();
        }

//      1.5 VIP奖分佣逻辑【无限代极差】
        Long userId_3 = userId;
        user = sysUserService.selectUserById(userId);
        Long parentId3 = user.getParentId();
        int k = 0;// 递归到的次数
        Long downVipGrade = 0L;// 下级VIP等级
        Long upVipGrade = 0L;// 上级VIP等级
        while (StringUtils.isNotNull(parentId3) && parentId3.longValue() > 0) {// 有上级
            QxUserAcc parentAcc = qxUserAccService.selectQxUserAccByUserId(parentId3);
            SysUser parentUser = sysUserService.selectUserById(parentId3);
            if (StringUtils.isNull(parentAcc)) {
                if (StringUtils.isNotNull(parentUser)) {
                    parentId3 = parentUser.getParentId();
                } else {
                    parentId3 = 0L;
                }
                continue;
            }

            Long nowVipGrade = parentAcc.getVipGrade();// 现在的上级VIP等级
            if (0L!=nowVipGrade) {// 是VIP

                // 1.分佣记录
                QxVipCfg vipRule = qxVipCfgService.selectQxVipCfgById(nowVipGrade);
                QxVipRec share = new QxVipRec();
                share.setUserId(parentId3);
                share.setFromId(userId);

                k = k + 1;
                share.setEra((long) k);
                
                if (k == 1) {// 第1次 直接计算分佣
                    downVipGrade = nowVipGrade;
                    share.setNum(
                            totalPrice.multiply(vipRule.getRatio()).setScale(2, BigDecimal.ROUND_HALF_UP));// 分佣数量
                    share.setRatio(vipRule.getRatio());// 分佣比例
                } else {// 超过2次

                    // 如果 上级<下级 跳过该次分佣
                    if (downVipGrade > nowVipGrade) {
                        log.debug("[{}]对比[{}]上级VIP等级[A{}]<=下级VIP等级[A{}],跳过分佣。", parentId,userId_2, nowVipGrade, downVipGrade);
                        parentId = parentUser.getParentId();
                        continue;
                    }else if(downVipGrade == nowVipGrade) {// 如果 上级=下级 平级则下级吃到分佣
                        log.debug("[{}]对比[{}]上级VIP等级[A{}]==下级VIP等级[A{}],平级则下级吃到分佣。", parentId,userId_2, nowVipGrade, downVipGrade);
                    }

                    upVipGrade = nowVipGrade;// 当前变上级
                    log.debug("对比VIP等级:downVipGrade[{}]-upVipGrade[{}]", downVipGrade, upVipGrade);
                    // 计算级差
                    QxVipCfg VipRule2 = qxVipCfgService.selectQxVipCfgById(downVipGrade);
                    BigDecimal shareRatio = vipRule.getRatio().subtract(VipRule2.getRatio());

                    share.setNum(totalPrice.multiply(shareRatio).setScale(2, BigDecimal.ROUND_HALF_UP));// 扣完极差后的分佣数量
                    share.setRatio(shareRatio);// 扣完极差后的分佣比例

                    downVipGrade = upVipGrade;// 上级变下级

                }
                if ((share.getRatio().compareTo(BigDecimal.ZERO)==0)==false) {// 非平级
                    qxVipRecService.insertQxVipRec(share);

                    BalanceChange c5 = new BalanceChange();
                    c5.setUserId(parentId3);
                    c5.setAvailable(share.getNum());
                    c5.setCoinId(1L);
                    c5.setCoinName("USDT");
                    c5.setIncomeType(CoinConstants.IncomeType.INCOME);
                    c5.setMainType(CoinConstants.MainType.AIRDROP);
                    c5.setSonType("buyGiftVip");
                    cList.add(c5);
                }
                
            }

            parentId3 = parentUser.getParentId();
            userId_3 = parentAcc.getUserId();
        }
        
        // 资产操作
        boolean ok = coinAccountService.balanceChangeSYNC(cList);
        if (ok == false) {// 财务操作失败报错余额不足
            log.error("购买礼包失败！！！");
            throw new AccountBalanceNotEnoughException();
        }
        
    }

    /** 当前用户直推的用户集合 **/
    private List<UserTeam> directPushUser(long userId, List<UserTeam> userList) {
        List<UserTeam> userTeams = new ArrayList<>();
        for (UserTeam userTeam : userList) {
            if (userTeam.getParentId() == userId) {
                userTeams.add(userTeam);
            }
        }
        return userTeams;
    }

    public Map<Long, QxUserAcc> getAccMap(List<QxUserAcc> list) {
        return list.stream().collect(Collectors.toMap(QxUserAcc::getUserId, Function.identity(), (key1, key2) -> key2));
    }

    public Map<Long, QxLayerCfg> getLayerCfgMap(List<QxLayerCfg> list) {
        return list.stream().collect(Collectors.toMap(QxLayerCfg::getEra, Function.identity(), (key1, key2) -> key2));
    }

    /**
     * 查询指定用户的有效直推人数(有效账户=购买过礼包)
     */
    public Integer getUserDirect(Long userId, List<UserTeam> directList, Map<Long, QxUserAcc> accMap) {
        Integer v = 0;
        for (UserTeam userTeam : directList) {
            QxUserAcc acc = accMap.get(userTeam.getUserId());
            if (StringUtils.isNotNull(acc)) {
                v = v + 1;
            }
        }
        return v;
    }

    /**
     * 查询指定用户的代数 分享奖比例
     */
    public BigDecimal getUserShareRatio(Long userId, List<UserTeam> directList, Map<Long, QxUserAcc> accMap,
            Long grade) {
        Integer v = 0;
        for (UserTeam userTeam : directList) {
            QxUserAcc acc = accMap.get(userTeam.getUserId());
            if (StringUtils.isNotNull(acc) && grade.longValue() <= acc.getGiftGrade().longValue()) {
                v = v + 1;
            }
        }

        QxGiftCfg cfg = qxGiftCfgService.selectQxGiftCfgById(grade);

        if (v.intValue() >= 7) {
            return cfg.getRec7();
        } else if (v.intValue() >= 3) {
            return cfg.getRec3();
        } else {
            return cfg.getRec1();
        }

    }

}
