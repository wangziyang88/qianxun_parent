package com.qianxun.project.coin.udun;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.qianxun.project.coin.service.ICoinWalletService;

@Service
public class BiPayService {
    @Autowired
    private BiPayClient biPayClient;
    @Value("${bipay.host}")
    private String host;
    @Value("#{'${bipay.supported-coins}'.split(',')}")
    private List<String> supportedCoins;
    
    @Autowired
    private ICoinWalletService coinWalletService;

    public boolean isSupportedCoin(String coinName) {
        return supportedCoins != null && supportedCoins.contains(coinName);
    }

    /**
     * 创建币种地址
     *
     * @param coinType 币种
     * @param alias
     * @param walletId
     * @return
     */
    public Address createCoinAddress(CoinType coinType, String alias, String walletId) {
        //String realCallbackUrl = StringUtils.isEmpty(callbackUrl) ? (host + "/bipay/notify") : callbackUrl;
        String callbackUrl = host + "/bipay/notify";
        try {
            ResponseMessage<Address> resp = biPayClient.createCoinAddress(coinType.getCode(), callbackUrl, alias, walletId);
            return resp.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 提币
     * @param orderId
     * @param amount
     * @param coinType
     * @param subCoinType
     * @param address
     * @param memo
     * @return
     */
    public ResponseMessage<String> transfer(String orderId, BigDecimal amount, CoinType coinType, String subCoinType, String address, String memo) {
        String callbackUrl = host + "/bipay/notify";
        try {
            ResponseMessage<String> resp = biPayClient.transfer(orderId, amount, coinType.getCode(), subCoinType, address, callbackUrl, memo);
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseMessage.error("提交转币失败");
    }

    /**
     * 代付
     * @param orderId
     * @param amount
     * @param coinType
     * @param subCoinType
     * @param address
     * @param memo
     * @return
     */
    public ResponseMessage<String> autoTransfer(String orderId, BigDecimal amount, CoinType coinType, String subCoinType, String address, String memo) {
        String callbackUrl = host + "/bipay/notify";
        try {
            ResponseMessage<String> resp = biPayClient.autoTransfer(orderId, amount, coinType.getCode(), subCoinType, address, callbackUrl, memo);
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseMessage.error("提交转币失败");
    }

    public List<Transaction> queryTransaction() throws Exception {
        return biPayClient.queryTransaction("", "", "", null, "", "", "");
    }

    /**
     * 校验地址合法性
     * @param mainCoinType
     * @param address
     * @return
     * @throws Exception
     */
    public boolean checkAddress(String mainCoinType, String address) throws Exception {
        return biPayClient.checkAddress(mainCoinType, address);
    }

    /**
     * 查询支持币种
     * @param showBalance
     * @return
     * @throws Exception
     */
    public List<SupportCoin> getSupportCoin(Boolean showBalance) throws Exception {
        return biPayClient.getSupportCoin(showBalance);
    }
    
    /**
     * 充值到账回调
     * @param Trade 交易实体
     * @return
     * @throws Exception
     */
    public boolean rechargeCallback(Trade trade) throws Exception {
    	
    	//金额为最小单位，需要转换,包括amount和fee字段
        BigDecimal amount = trade.getAmount().divide(BigDecimal.TEN.pow(trade.getDecimals()),8, RoundingMode.DOWN);
        BigDecimal fee = trade.getFee().divide(BigDecimal.TEN.pow(trade.getDecimals()),8, RoundingMode.DOWN);
        trade.setAmount(amount);
        trade.setFee(fee);
        
        boolean rs = false;
        Long coinId = 0L;
        if (trade.isUsdt() || trade.isErcToken() || trade.isTrcToken()) {// USDT
            coinId = 1L;
            rs = coinWalletService.handleRecharge(trade.getTradeId(), trade.getTxId(), trade.getAddress(), trade.getBlockHigh(), amount, coinId, trade.getMemo());
        }
        return rs;
    }
    
    /**
     * 提现一条龙回调
     * @param Trade 交易实体
     * @return
     * @throws Exception
     */
    public boolean withdrawCallback(Trade trade) throws Exception {
    	
    	//金额为最小单位，需要转换,包括amount和fee字段
        BigDecimal amount = trade.getAmount().divide(BigDecimal.TEN.pow(trade.getDecimals()),8, RoundingMode.DOWN);
        BigDecimal fee = trade.getFee().divide(BigDecimal.TEN.pow(trade.getDecimals()),8, RoundingMode.DOWN);
        trade.setAmount(amount);
        trade.setFee(fee);
        
        boolean rs = coinWalletService.handleThirdpartyWithdrawal(trade);
//        boolean rs = true;
        return rs;
    }
}
