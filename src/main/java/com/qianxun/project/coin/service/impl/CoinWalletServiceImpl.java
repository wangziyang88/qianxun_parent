package com.qianxun.project.coin.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.qianxun.common.enums.CoinConstants;
import com.qianxun.common.enums.CoinConstants.IncomeType;
import com.qianxun.common.enums.CoinConstants.MainType;
import com.qianxun.common.enums.CoinConstants.RecordStatus;
import com.qianxun.common.exception.BaseException;
import com.qianxun.common.exception.coin.AccountOperationBusyException;
import com.qianxun.common.exception.user.ParameterException;
import com.qianxun.common.exception.user.SystemBusyException;
import com.qianxun.common.utils.DateUtils;
import com.qianxun.common.utils.MessageUtils;
import com.qianxun.common.utils.StringUtils;
import com.qianxun.framework.redis.CacheUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qianxun.project.coin.mapper.CoinWalletMapper;
import com.qianxun.project.coin.domain.CoinRecord;
import com.qianxun.project.coin.domain.CoinWallet;
import com.qianxun.project.coin.domain.vo.BalanceChange;
import com.qianxun.project.coin.service.ICoinAccountService;
import com.qianxun.project.coin.service.ICoinRecordService;
import com.qianxun.project.coin.service.ICoinTypeService;
import com.qianxun.project.coin.service.ICoinWalletService;
import com.qianxun.project.coin.udun.Address;
import com.qianxun.project.coin.udun.BiPayService;
import com.qianxun.project.coin.udun.CoinType;
import com.qianxun.project.coin.udun.ResponseMessage;
import com.qianxun.project.coin.udun.Trade;

@Service
public class CoinWalletServiceImpl implements ICoinWalletService {
    @Autowired
    private CoinWalletMapper coinWalletMapper;

    private Logger log = LoggerFactory.getLogger(CoinWalletServiceImpl.class);

    @Autowired
    private BiPayService biPayService;

    @Autowired
    private ICoinTypeService coinTypeService;

    @Autowired
    private ICoinAccountService coinAccountService;

    @Autowired
    private ICoinRecordService coinRecordService;

    @Autowired
    private CacheUtils cacheUtils;

    @Override
    public CoinWallet selectCoinWalletById(Long id) {
        return coinWalletMapper.selectCoinWalletById(id);
    }

    @Override
    public List<CoinWallet> selectCoinWalletList(CoinWallet coinWallet) {
        return coinWalletMapper.selectCoinWalletList(coinWallet);
    }

    @Override
    public int insertCoinWallet(CoinWallet coinWallet) {
        coinWallet.setCreateTime(DateUtils.getNowDate());
        return coinWalletMapper.insertCoinWallet(coinWallet);
    }

    @Override
    public int updateCoinWallet(CoinWallet coinWallet) {
        coinWallet.setUpdateTime(DateUtils.getNowDate());
        return coinWalletMapper.updateCoinWallet(coinWallet);
    }

    @Override
    public int deleteCoinWalletByIds(Long[] ids) {
        return coinWalletMapper.deleteCoinWalletByIds(ids);
    }

    @Override
    public int deleteCoinWalletById(Long id) {
        return coinWalletMapper.deleteCoinWalletById(id);
    }
    
    @Override
    public CoinWallet selectCoinWalletByAddress(String address) {
        return coinWalletMapper.selectCoinWalletByAddress(address);
    }

    // 优盾获取地址实现
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<CoinWallet> selectCoinWalletByCoinIdListToUdun(Long coinId, Long userId) {
        CoinWallet cw = new CoinWallet();
        cw.setCoinId(coinId);
        cw.setUserId(userId);
        List<CoinWallet> list = coinWalletMapper.selectCoinWalletList(cw);//通过用户id 币种id查询钱包列表
        com.qianxun.project.coin.domain.CoinType coinType = coinTypeService.selectCoinTypeById(coinId);
        if (StringUtils.isNotNull(coinType) && coinType.getId().longValue() == 1) {// USDT
            if (StringUtils.isEmpty(list)) {  //没有钱包数据  创造优盾地址
//                Address addr = biPayService.createCoinAddress(CoinType.Bitcoin, userId.toString(), "");
//                if (StringUtils.isNotNull(addr)) {
//                    cw.setAddress(addr.getAddress());
//                    cw.setCoinName("USDT");
//                    cw.setWalletType("thirdparty");
//                    cw.setMainCoinType("omni");
//                    insertCoinWallet(cw);
//                    list.add(cw);//将生成的钱包信息加到集合
//                }
                Address addr_eth = biPayService.createCoinAddress(CoinType.Ethereum, userId.toString(), "");
                if (StringUtils.isNotNull(addr_eth)) {
                    cw.setAddress(addr_eth.getAddress());
                    cw.setCoinName("USDT");
                    cw.setWalletType("thirdparty");
                    cw.setMainCoinType("erc20");
                    insertCoinWallet(cw);
                    list.add(cw);
                }
//                Address addr_trx = biPayService.createCoinAddress(CoinType.TRX, userId.toString(), "");
//                if (StringUtils.isNotNull(addr_trx)) {
//                    cw.setAddress(addr_trx.getAddress());
//                    cw.setCoinName("USDT");
//                    cw.setWalletType("thirdparty");
//                    cw.setMainCoinType("trc20");
//                    insertCoinWallet(cw);
//                    list.add(cw);
//                }
            }
        }

        return list;
    }

    /**
     * 处理充值到账
     * 
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean handleRecharge(String tradeId, String txId, String address, String blockHigh, BigDecimal amount,
            Long coinId, String memo) {
        
        CoinRecord record = coinRecordService.selectCoinRecordByTxId(txId); //通过交易id查询流水记录
        if (StringUtils.isNotNull(record)) {
            log.error("重复充值Txid[{}]！！！", txId);
            return true;
        }

        List<BalanceChange> cList = new ArrayList<BalanceChange>();
        BalanceChange c = new BalanceChange();
        c.setAvailable(amount); //设置余额
        c.setCoinId(coinId); //设置币种
        com.qianxun.project.coin.domain.CoinType coinType = coinTypeService.selectCoinTypeById(coinId); //查到币种
        if (StringUtils.isNull(coinType)) {
            log.error("币种ID[{}]找不到！！！", coinId);
            return false;
        }
        //设置币种信息
        c.setCoinName(coinType.getCoinName());
       //c.setUserId(coinId);
        c.setIncomeType(IncomeType.INCOME);
        c.setMainType(MainType.RECHARGE);
        c.setSonType("recharge");
        
        CoinWallet wallet = selectCoinWalletByAddress(address); //通过地址查询到钱包信息
        if (StringUtils.isNull(wallet)) {
            log.error("参数异常-找到不到充值地址[{}]", address);
            return false;
        }

        c.setUserId(wallet.getUserId());
        cList.add(c);
        boolean sync = false;
        try {
            sync = coinAccountService.balanceChangeSYNC(cList);
        } catch (Exception e) {
            return false;// 资产变更异常
        }
        
        if (sync == false) {// 资产变更异常
            return false;
        }

        CoinRecord rec = new CoinRecord(); //添加流水记录
        rec.setUserId(c.getUserId());
        rec.setCoinId(c.getCoinId());
        rec.setCoinName(c.getCoinName());
        rec.setCreateTime(DateUtils.getNowDate());
        rec.setFee(BigDecimal.ZERO);
        rec.setIncomeType(c.getIncomeType().getType());
        rec.setMainType(c.getMainType().getType());
        rec.setSonType(c.getSonType());
        rec.setStatus(RecordStatus.OK.getStatus());
        rec.setAmount(c.getAvailable());
        rec.setFromAddress(null);
        rec.setToAddress(address);
        rec.setTxid(txId);
        rec.setMemo(memo);

        coinRecordService.insertCoinRecord(rec);

        return true;
    }

    // 发起提现
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void withdraw(CoinRecord cr){
        CoinWallet coinWallet = selectCoinWalletByAddress(cr.getToAddress());
        if (StringUtils.isNotNull(coinWallet) && coinWallet.getUserId().equals(cr.getUserId())){
            throw new BaseException(MessageUtils.message("coin.can.not.transfer.yourself"));//不能自己转给自己
        }

        //判断提现数量是否正确
        if (StringUtils.isNull(cr.getAmount()) || cr.getAmount().compareTo(BigDecimal.ZERO)<=0 ) {
            throw new ParameterException();
        }

        String mainCoinType = null; 
        String subCoinType = null;
        //判断为主链名
        if ("omni".equals(cr.getCoinName())) {
            mainCoinType = CoinType.Bitcoin.getCode();
            subCoinType = "31";
        }else if ("erc20".equals(cr.getCoinName())) {
            mainCoinType = CoinType.Ethereum.getCode();
            subCoinType = "0xdac17f958d2ee523a2206206994597c13d831ec7";
        }else if ("trc20".equals(cr.getCoinName())) {
            mainCoinType = CoinType.TRX.getCode();
            subCoinType = "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t";
        }
        // 判断地址是否正确
        try {
            boolean checkAddress = biPayService.checkAddress(mainCoinType, cr.getToAddress());
            if (checkAddress==false) {
                throw new ParameterException();
            }
        } catch (Exception e) {
            throw new ParameterException();
        }
        
        com.qianxun.project.coin.domain.CoinType coinType = coinTypeService.selectCoinTypeById(cr.getCoinId());
        if (StringUtils.isNull(coinType)) {
            log.error("币种ID[{}]找不到！！！", cr.getCoinId());
            return;
        }
        // 判断最低提现额度
        if (cr.getAmount().compareTo(coinType.getWithdrawalMin()) < 0) {
            throw new ParameterException();
        }
        
        List<BalanceChange> cList = new ArrayList<BalanceChange>();
        
        /**链上转账**/
        CoinWallet wallet = selectCoinWalletByAddress(cr.getToAddress());//通过地址查询到钱包数据
        //判断钱包数据是否为空
        if (StringUtils.isNull(wallet)) {
            BalanceChange c = new BalanceChange();
            c.setAvailable(cr.getAmount().negate());//设置数量为负
            c.setFrozen(cr.getAmount()); //设置冻结金额
            c.setCoinId(cr.getCoinId());
            c.setUserId(cr.getUserId());
            c.setIncomeType(IncomeType.PAYOUT);
            c.setMainType(MainType.FROZEN);
            c.setSonType("withdrawal");
            cList.add(c);
            
            boolean sync = coinAccountService.balanceChangeSYNC(cList);
            if (sync == false) {// 资产变更异常
                throw new AccountOperationBusyException();
            }
            
            // 插入记录
            cr.setIncomeType(IncomeType.PAYOUT.getType());
            cr.setMainType(MainType.WITHDRAWAL.getType());
            cr.setSonType("withdrawal");
            cr.setStatus(RecordStatus.WAIT.getStatus());
            cr.setCoinName(coinType.getCoinName());
            
            // 手续费计算》》》》》》》》》
            BigDecimal fee = coinType.getWithdrawalFee();// 固定额度
            BigDecimal feeRate = coinType.getWithdrawalFeeRate().multiply(cr.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP);// 比例
            cr.setFee(fee.add(feeRate));
            
            coinRecordService.insertCoinRecord(cr);
            
            // 发起提现申请
            CoinType coin = CoinType.codeOf(Integer.parseInt(mainCoinType));
            ResponseMessage<String> resp = biPayService.transfer(CoinConstants.BUSINESSID+cr.getId(), cr.getAmount().subtract(cr.getFee()), coin, subCoinType, cr.getToAddress(), cr.getMemo());
            if(resp.getCode()!=200) {
                throw new SystemBusyException();
            }
            
        }else {/**内转**/
            /*********** 转出方 *************/
            cr.setToId(wallet.getUserId());
            
            BalanceChange c = new BalanceChange();
            c.setAvailable(cr.getAmount().negate());
            c.setCoinId(cr.getCoinId());
            c.setUserId(cr.getUserId());
            c.setIncomeType(IncomeType.PAYOUT);
            c.setMainType(MainType.WITHDRAWAL);
            c.setSonType("withdrawal");
            cList.add(c);
            
            // 资产记录
            cr.setIncomeType(IncomeType.PAYOUT.getType());
            cr.setMainType(MainType.WITHDRAWAL.getType());
            cr.setSonType("withdrawal");
            cr.setStatus(RecordStatus.OK.getStatus());
            cr.setCoinName(coinType.getCoinName());
            
            /*********** 转出方 *************/
            
            /*********** 转入 *************/
            BalanceChange toc = new BalanceChange();
            toc.setAvailable(cr.getAmount());
            toc.setCoinId(cr.getCoinId());
            toc.setUserId(wallet.getUserId());
            toc.setIncomeType(IncomeType.INCOME);
            toc.setMainType(MainType.RECHARGE);
            toc.setSonType("recharge");
            cList.add(toc);
            
            CoinRecord tocr = new CoinRecord();
            tocr.setUserId(wallet.getUserId());
            tocr.setAmount(cr.getAmount());
            tocr.setCoinId(cr.getCoinId());
            tocr.setCoinName(coinType.getCoinName());
            tocr.setIncomeType(IncomeType.INCOME.getType());
            tocr.setMainType(MainType.RECHARGE.getType());
            tocr.setSonType("recharge");
            tocr.setStatus(RecordStatus.OK.getStatus());
            tocr.setFromAddress(wallet.getAddress());//来的地址
            
            /*********** 转入 *************/
            
            boolean sync = coinAccountService.balanceChangeSYNC(cList);
            if (sync == false) {// 资产变更异常
                throw new AccountOperationBusyException();
            }

            coinRecordService.insertCoinRecord(cr);
            coinRecordService.insertCoinRecord(tocr);
            
        }
        
    }
    
    
    /**
     * 处理第三方提币审核结果 [审核通过][审核拒绝][到账返回Txid]
     * 
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean handleThirdpartyWithdrawal(Trade trade) {
        
        // 查询提币订单，没有就返回false
        CoinRecord cr = null;
        if (StringUtils.isNotNull(trade) && StringUtils.isNotNull(trade.getBusinessId())) {
            //移除企业编号
            Long businessId = Long.parseLong(trade.getBusinessId().replace(CoinConstants.BUSINESSID, ""));
            cr = coinRecordService.selectCoinRecordById(businessId);
        }
        if (StringUtils.isNull(cr)) {
            return false;
        }
        //判断是否为提款，不是就返回false
        if (trade.getTradeType()!=2) {
            return false;
        }
        
        // [审核通过]判断是否审核中 扣除冻结 
        if (trade.getStatus()==1 && CoinConstants.RecordStatus.WAIT.getStatus().equals(cr.getStatus())) {
            cr.setStatus(CoinConstants.RecordStatus.PASS.getStatus());
            List<BalanceChange> cList = new ArrayList<BalanceChange>();
            BalanceChange c = new BalanceChange();
            c.setFrozen(cr.getAmount().negate());
            c.setCoinId(cr.getCoinId());
            c.setUserId(cr.getUserId());
            c.setIncomeType(IncomeType.PAYOUT);
            c.setMainType(MainType.WITHDRAWAL);
            c.setSonType("withdrawal");
            cList.add(c);
            
            boolean sync = coinAccountService.balanceChangeSYNC(cList);
            if (sync == false) {// 资产变更异常
                throw new AccountOperationBusyException();
            }

        }
        
        // [审核拒绝]判断是否审核中 解冻返回余额 返回Txid
        if (trade.getStatus()==2 && CoinConstants.RecordStatus.WAIT.getStatus().equals(cr.getStatus())) {
            cr.setStatus(CoinConstants.RecordStatus.REFUSE.getStatus());
            cr.setTxid(trade.getTxId());
            List<BalanceChange> cList = new ArrayList<BalanceChange>();
            BalanceChange c = new BalanceChange();
            c.setAvailable(cr.getAmount());
            c.setFrozen(cr.getAmount().negate());
            c.setCoinId(cr.getCoinId());
            c.setUserId(cr.getUserId());
            c.setIncomeType(IncomeType.PAYOUT);
            c.setMainType(MainType.WITHDRAWAL);
            c.setSonType("withdrawal");
            cList.add(c);
            
            boolean sync = coinAccountService.balanceChangeSYNC(cList);
            if (sync == false) {// 资产变更异常
                throw new AccountOperationBusyException();
            }
            
        }
        
        // [到账 和区块高度]
        if (trade.getStatus()==3) {
            cr.setStatus(CoinConstants.RecordStatus.OK.getStatus());
        }
        
        coinRecordService.updateCoinRecord(cr);
        return true;
    }



}
