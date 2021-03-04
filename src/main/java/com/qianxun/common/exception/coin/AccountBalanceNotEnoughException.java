package com.qianxun.common.exception.coin;

import com.qianxun.common.exception.CoinException;

/**
 * 账户余额不够
 */
public class AccountBalanceNotEnoughException extends CoinException{

	private static final long serialVersionUID = 1L;

	/**
	 * 账户余额不够
	 */
	public AccountBalanceNotEnoughException()
    {
    	super("coin.balanceNotEnough.error", null);
    }
}
