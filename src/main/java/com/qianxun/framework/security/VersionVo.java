package com.qianxun.framework.security;

import lombok.Data;

import java.util.Date;

/**
 * @Author:
 * @Email:
 * @Description: 返回版本信息
 * @Date:2021/1/26 16:39
 * @Version:1.0
 */
@Data
public class VersionVo {

    /**版本号*/
    private String versionNum;

    /**版本地址*/
    private String versionUrl;

    /**更新时间*/
    private Date updateTime;

}
