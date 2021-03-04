package com.qianxun.common.exception;

/**
 * 资金账户基础异常类
 * 
 * @author lv
 */
public class CoinException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public CoinException(String code, Object[] args)
    {
        super("coin", code, args, null);
    }
}
