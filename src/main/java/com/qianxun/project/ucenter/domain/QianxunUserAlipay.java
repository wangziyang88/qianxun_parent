package com.qianxun.project.ucenter.domain;

import com.qianxun.framework.web.controller.BaseController;
import com.qianxun.framework.web.domain.BaseEntity;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class QianxunUserAlipay extends BaseEntity {
    /**
     * ID
     */
    private Integer id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 支付宝二维码图片地址
     */
    @NotBlank(message = "{ucenter.codeaddr.is.empty}")
    private String codeAddr;

    /**
     * 支付宝账户名字
     */
    private String name;

    /**
     * 联络方式
     */
    private String contact;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCodeAddr() {
        return codeAddr;
    }

    public void setCodeAddr(String codeAddr) {
        this.codeAddr = codeAddr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

}