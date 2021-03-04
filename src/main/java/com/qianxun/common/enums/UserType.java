package com.qianxun.common.enums;

/**
 * @Author:
 * @Email:
 * @Description: 用户类型
 * @Date:2021/1/12 14:38
 * @Version:1.0
 */
public enum UserType {
    //系统用户
    SYSTEMUSER("00", "系统用户"),
    //普通用户
    GENERALUSER("01", "普通用户"),
    //代理
    PROXY("02", "代理");

    private final String code;
    private final String info;

    UserType(String code, String info){
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
