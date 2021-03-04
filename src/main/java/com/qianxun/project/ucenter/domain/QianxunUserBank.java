package com.qianxun.project.ucenter.domain;

import com.qianxun.framework.web.domain.BaseEntity;

import javax.validation.constraints.NotBlank;

public class QianxunUserBank extends BaseEntity {
    /**
     * ID
     */
    private Integer id;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 银行名字
     */
    @NotBlank(message = "{ucenter.bank.is.empty}")
    private String bankName;

    /**
     * 持有人姓名
     */
    private String ownerName;

    /**
     * 银行账号号码
     */
    @NotBlank(message = "{ucenter.bankcoard.is.empty}")
    private String accountNum;

    /**
     * 支行
     */
    private String branch;

    /**
     * 银行swift代码
     */
    private String swift;

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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }


}