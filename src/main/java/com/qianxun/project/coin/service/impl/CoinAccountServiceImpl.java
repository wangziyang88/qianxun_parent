package com.qianxun.project.coin.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.alibaba.fastjson.JSON;
import com.qianxun.common.enums.CoinConstants.MainType;
import com.qianxun.common.enums.CoinConstants.RecordStatus;
import com.qianxun.common.exception.coin.AccountBalanceNotEnoughException;
import com.qianxun.common.exception.coin.AccountOperationBusyException;
import com.qianxun.common.utils.DateUtils;
import com.qianxun.common.utils.SpringUtils;
import com.qianxun.common.utils.StringUtils;
import com.qianxun.framework.redis.CacheUtils;
import com.qianxun.project.coin.domain.CoinAccount;
import com.qianxun.project.coin.domain.CoinRecord;
import com.qianxun.project.coin.domain.CoinType;
import com.qianxun.project.coin.domain.vo.BalanceChange;
import com.qianxun.project.coin.mapper.CoinAccountMapper;
import com.qianxun.project.coin.service.ICoinAccountService;
import com.qianxun.project.coin.service.ICoinRecordService;
import com.qianxun.project.coin.service.ICoinTypeService;

@Service
public class CoinAccountServiceImpl implements ICoinAccountService {

    protected final Logger log = LoggerFactory.getLogger(CoinAccountServiceImpl.class);

    @Autowired
    private CoinAccountMapper coinAccountMapper;

    @Autowired
    private ICoinTypeService coinTypeService;
    
    @Autowired
    private ICoinRecordService coinRecordService;

    @Autowired
    private CacheUtils cacheUtils;

    @Override
    public CoinAccount selectCoinAccountById(Long id) {
        return coinAccountMapper.selectCoinAccountById(id);
    }

    @Override
    public List<CoinAccount> selectCoinAccountList(CoinAccount coinAccount) {
        return coinAccountMapper.selectCoinAccountList(coinAccount);
    }

    @Override
    public int insertCoinAccount(CoinAccount coinAccount) {
        coinAccount.setCreateTime(DateUtils.getNowDate());
        return coinAccountMapper.insertCoinAccount(coinAccount);
    }

    @Override
    public int updateCoinAccount(CoinAccount coinAccount) {
        coinAccount.setUpdateTime(DateUtils.getNowDate());
        return coinAccountMapper.updateCoinAccount(coinAccount);
    }

    @Override
    public int deleteCoinAccountByIds(Long[] ids) {
        return coinAccountMapper.deleteCoinAccountByIds(ids);
    }

    @Override
    public int deleteCoinAccountById(Long id) {
        return coinAccountMapper.deleteCoinAccountById(id);
    }

    @Override
    public List<CoinAccount> selectCoinAccountByUserId(Long userId) {
        return processCoinAccount(userId);
    }

    /**
     * 创建用户 【资金账户】
     */
    private List<CoinAccount> processCoinAccount(Long userId) throws AccountOperationBusyException {
        CoinType c = new CoinType();
        c.setStatus("1");
        List<CoinType> coinList = coinTypeService.selectCoinTypeList(c);//查询到所有启用的币种
        CoinAccount a = new CoinAccount();
        a.setUserId(userId);
        List<CoinAccount> accountList = coinAccountMapper.selectCoinAccountList(a);//查询用户的账户列表

        if (StringUtils.isNotEmpty(coinList)) {
            if (StringUtils.isEmpty(accountList) || accountList.size() < coinList.size()) {//用户没有账户或者账户数量小于币种数量
                String lockName = "userLock:" + userId;
                boolean isLock = cacheUtils.getLock(lockName, CacheUtils.LOCK_WAITTIME_SECONDS);//获得锁

                try {
                    if (isLock) { //获取锁成功

                        DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) SpringUtils
                                .getBean("transactionManager");
                        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                        TransactionStatus status = transactionManager.getTransaction(def);

                        try {
                            List<CoinAccount> rsList = new ArrayList<CoinAccount>();
                            for (CoinType coin : coinList) {  //遍历启用的币种
                                CoinAccount account = coinAccountMapper.getByUserIdAndCoinId(userId, coin.getId());//通过用户id和币种id查询账户
                                if (account == null && "1".equals(coin.getStatus())) {/** 状态（0禁用 1启用） */
                                    Date d = DateUtils.getNowDate();
                                    account = new CoinAccount();
                                    //封装新账户数据
                                    account.setAvailable(BigDecimal.ZERO);
                                    account.setFrozen(BigDecimal.ZERO);
                                    account.setLockup(BigDecimal.ZERO);
                                    account.setUserId(userId);
                                    account.setCoinId(coin.getId());
                                    account.setCoinName(coin.getCoinName());
                                    account.setVersion(0L);
                                    account.setCreateTime(d);
                                    account.setUpdateTime(d);
                                    coinAccountMapper.insertCoinAccount(account);//添加账户数据
                                    rsList.add(account);//将创建好的账户放入集合
                                }

                            }

                            transactionManager.commit(status); //提交事务
                            return rsList; //返回账户列表
                        } catch (Exception e) {
                            e.printStackTrace();
                            transactionManager.rollback(status); //事务回滚
                            throw e;
                        }
                    }
                    throw new AccountOperationBusyException();
                } catch (Exception e) {
                    throw e;
                } finally {
                    if (isLock) {
                        cacheUtils.releaseLock(lockName); //释放锁
                    }
                }
            } else {
                return accountList;
            }
        }
        return new ArrayList<CoinAccount>(); //无启用币种时候
    }

    @Override
    public CoinAccount getAccountByUserIdAndCoinId(Long userId, Long coinId) throws AccountOperationBusyException {
        CoinAccount account = coinAccountMapper.getByUserIdAndCoinId(userId, coinId);
        if (account == null) {
            //没有查到就去创建
            List<CoinAccount> accountList = processCoinAccount(userId);
            if (StringUtils.isNotEmpty(accountList)) {
                for (CoinAccount a : accountList) {
                    if (a.getCoinId() == coinId) {
                        return a;
                    }
                }
            }
            return null;
        }
        return account;
    }

    /** 同步-操作资产 [余额/冻结/锁仓] **/
    @Override
    public boolean balanceChangeSYNC(List<BalanceChange> cList)
            throws AccountBalanceNotEnoughException, AccountOperationBusyException {

        if (StringUtils.isEmpty(cList)) {
            return false;// 无操作
        }

        for (BalanceChange c : cList) {
            // 判断参数
            if (StringUtils.isNull(c.getCoinId()) || StringUtils.isNull(c.getUserId())
                    || StringUtils.isNull(c.getMainType())) {
                log.error("参数异常{}", JSON.toJSON(c));
                return false;
            }

            if (StringUtils.isNull(c.getAvailable()) && StringUtils.isNull(c.getFrozen())
                    && StringUtils.isNull(c.getLockup())) {
                log.error("参数异常{}", JSON.toJSON(c));
                return false;
            }

            CoinAccount acc = getAccountByUserIdAndCoinId(c.getUserId(), c.getCoinId()); //通过用户id和币种id查询账户
            if (StringUtils.isNull(acc)) {
                log.error("参数异常-{},找到不到账户", JSON.toJSON(c));
                return false;
            }
            c.setCoinName(acc.getCoinName());
            log.info("操作用户ID[{}]币种ID[{}-{}]余额数量[{}]冻结数量[{}]锁仓数量[{}],操作主类型[{}],操作子类型[{}]", c.getUserId(), c.getCoinId(), c.getCoinName(),
                    c.getAvailable(), c.getFrozen(), c.getLockup(), c.getMainType().getRemark(), c.getSonType());

            if (StringUtils.isNotNull(c.getAvailable()) && c.getAvailable().compareTo(BigDecimal.ZERO) != 0) {//判断 操作剩余金额
                if (acc.getAvailable().add(c.getAvailable()).compareTo(BigDecimal.ZERO) == -1) {
                    throw new AccountBalanceNotEnoughException();
                } else {
                    acc.setAvailable(acc.getAvailable().add(c.getAvailable()));
                }
            }

            if (StringUtils.isNotNull(c.getFrozen()) && c.getFrozen().compareTo(BigDecimal.ZERO) != 0) {//判断 操作冻结金额
                if (acc.getFrozen().add(c.getFrozen()).compareTo(BigDecimal.ZERO) == -1) {
                    throw new AccountBalanceNotEnoughException();
                } else {
                    acc.setFrozen(acc.getFrozen().add(c.getFrozen()));
                }
            }

            if (StringUtils.isNotNull(c.getLockup()) && c.getLockup().compareTo(BigDecimal.ZERO) != 0) { //判断 操作锁仓金额
                if (acc.getLockup().add(c.getLockup()).compareTo(BigDecimal.ZERO) == -1) {
                    throw new AccountBalanceNotEnoughException();
                } else {
                    acc.setLockup(acc.getLockup().add(c.getLockup()));
                }
            }
            c.setCoinName(acc.getCoinName());
            boolean isLock = cacheUtils.getAccountLock(acc.getId(), CacheUtils.LOCK_WAITTIME_SECONDS);//获得锁
            try {
                Date d = DateUtils.getNowDate();
                if (!isLock || coinAccountMapper.balanceChange(acc.getId(), acc.getAvailable(), acc.getFrozen(), //没有获取到锁或者没有更新成功
                        acc.getLockup(), acc.getVersion(), d) <= 0) {
                    throw new AccountOperationBusyException();
                } else {
                    if (MainType.FROZEN.equals(c.getMainType()) || MainType.UNFREEZE.equals(c.getMainType())
                            || MainType.LOCKUP.equals(c.getMainType()) || MainType.UNLOCK.equals(c.getMainType())) {
                        // 冻结/解冻/锁仓/解锁 不生成资产流水
                    }else if (MainType.RECHARGE.equals(c.getMainType()) || MainType.WITHDRAWAL.equals(c.getMainType())) {
                        // 充值/提现 单独生成资产流水
                    }else {
                        CoinRecord rec = new CoinRecord();
                        rec.setUserId(c.getUserId());
                        rec.setCoinId(c.getCoinId());
                        rec.setCoinName(c.getCoinName());
                        rec.setCreateTime(d);
                        rec.setFee(BigDecimal.ZERO);
                        rec.setIncomeType(c.getIncomeType().getType());
                        rec.setMainType(c.getMainType().getType());
                        rec.setSonType(c.getSonType());
                        rec.setStatus(RecordStatus.OK.getStatus());
                        rec.setAmount(c.getAvailable());
                        
                        coinRecordService.insertCoinRecord(rec);
                    }
                }
            } catch (Exception e) {
                throw e;
            } finally {
                if (isLock) {
                    cacheUtils.releaseAccountLock(acc.getId());
                }
            }

        }

        return true;
    }

}
