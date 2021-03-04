package com.qianxun.common.enums;

/**
 * @Author:
 * @Email:
 * @Description:
 * @Date:2021/3/1 16:33
 * @Version:1.0
 */
public enum OrderStatus {
    CANCELLED("0", "已取消"),
    NOHAVEVOUCHER("1", "未上传凭证"),
    HAVEVOUCHER("2", "已上传凭证"),
    COMPLETED("3","已完成");

    private final String code;
    private final String info;

    OrderStatus(String code, String info)
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
