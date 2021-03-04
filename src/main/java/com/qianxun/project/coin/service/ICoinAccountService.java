package com.qianxun.project.coin.service;

import java.util.List;

import com.qianxun.common.exception.coin.AccountBalanceNotEnoughException;
import com.qianxun.common.exception.coin.AccountOperationBusyException;
import com.qianxun.project.coin.domain.CoinAccount;
import com.qianxun.project.coin.domain.vo.BalanceChange;

public interface ICoinAccountService 
{
    public CoinAccount selectCoinAccountById(Long id);

    public List<CoinAccount> selectCoinAccountList(CoinAccount coinAccount);

    public int insertCoinAccount(CoinAccount coinAccount);

    public int updateCoinAccount(CoinAccount coinAccount);

    public int deleteCoinAccountByIds(Long[] ids);

    public int deleteCoinAccountById(Long id);

    List<CoinAccount> selectCoinAccountByUserId(Long userId);

    CoinAccount getAccountByUserIdAndCoinId(Long userId, Long coinId) throws AccountOperationBusyException;

    // 同步操作资产[余额/冻结/锁仓]
    boolean balanceChangeSYNC(List<BalanceChange> cList)
            throws AccountBalanceNotEnoughException, AccountOperationBusyException;
}
