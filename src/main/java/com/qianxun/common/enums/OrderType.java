package com.qianxun.common.enums;

/**
 * @Author:
 * @Email:
 * @Description:
 * @Date:2021/3/1 16:32
 * @Version:1.0
 */
public enum OrderType {
    BUY("1", "买"),
    SELL("0", "卖");

    private final String code;
    private final String info;

    OrderType(String code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public String getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }
}
