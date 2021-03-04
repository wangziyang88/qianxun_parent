package com.qianxun.project.ucenter.domain;

import com.qianxun.framework.web.domain.BaseEntity;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class QianxunUserExternal extends BaseEntity {
    /**
     * ID
     */
    private Integer id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 外部地址图片
     */
    @NotBlank(message = "{ucenter.codeaddr.is.empty}")
    private String codeAddr;

    /**
     * 外部地址USDT(TRC)地址
     */
    @NotBlank(message = "{ucenter.usdt.is.empty}")
    private String externalAddr;

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

    public String getExternalAddr() {
        return externalAddr;
    }

    public void setExternalAddr(String externalAddr) {
        this.externalAddr = externalAddr;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

}