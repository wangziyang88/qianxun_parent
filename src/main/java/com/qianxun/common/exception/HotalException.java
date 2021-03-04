package com.qianxun.common.exception;

/**
 * @Author:
 * @Email:
 * @Description:
 * @Date:2021/1/28 11:54
 * @Version:1.0
 */
public class HotalException extends BaseException{
       private static final long serialVersionUID = 1L;

    public HotalException(String code, Object[] args) {
        super("hotal", code, args, null);
    }
}
