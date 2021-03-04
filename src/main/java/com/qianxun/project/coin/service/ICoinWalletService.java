package com.qianxun.project.coin.service;

import java.math.BigDecimal;
import java.util.List;

import com.qianxun.project.coin.domain.CoinRecord;
import com.qianxun.project.coin.domain.CoinWallet;
import com.qianxun.project.coin.udun.Trade;

public interface ICoinWalletService 
{
    public CoinWallet selectCoinWalletById(Long id);

    public List<CoinWallet> selectCoinWalletList(CoinWallet coinWallet);

    public int insertCoinWallet(CoinWallet coinWallet);

    public int updateCoinWallet(CoinWallet coinWallet);

    public int deleteCoinWalletByIds(Long[] ids);

    public int deleteCoinWalletById(Long id);

    List<CoinWallet> selectCoinWalletByCoinIdListToUdun(Long coinId, Long userId);

    /** 处理充值到账**/
    boolean handleRecharge(String tradeId, String txId, String address, String blockHigh, BigDecimal amount,
            Long coinId, String memo);

    CoinWallet selectCoinWalletByAddress(String address);

    /** 发起提现
     * @throws Exception **/
    public void withdraw(CoinRecord coinRecord);

    /**
     * 处理第三方提币审核结果 [审核通过][审核拒绝][到账返回Txid]
     */
    boolean handleThirdpartyWithdrawal(Trade trade);
}
