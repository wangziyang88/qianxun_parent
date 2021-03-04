package com.qianxun.project.coin.mapper;

import java.util.List;
import com.qianxun.project.coin.domain.CoinWallet;

public interface CoinWalletMapper 
{
    public CoinWallet selectCoinWalletById(Long id);

    public List<CoinWallet> selectCoinWalletList(CoinWallet coinWallet);

    public int insertCoinWallet(CoinWallet coinWallet);

    public int updateCoinWallet(CoinWallet coinWallet);

    public int deleteCoinWalletById(Long id);

    public int deleteCoinWalletByIds(Long[] ids);

    public CoinWallet selectCoinWalletByAddress(String address);
}
