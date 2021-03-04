package com.qianxun.project.coin.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.qianxun.framework.aspectj.lang.annotation.Excel;
import com.qianxun.framework.web.domain.BaseEntity;

public class CoinType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "币种名")
    private String coinName;

    @Excel(name = "币种全称")
    private String coinFullName;
    
    @Excel(name = "最少提现")
    private BigDecimal withdrawalMin;
    
    @Excel(name = "提现手续费[固定]")
    private BigDecimal withdrawalFee;
    
    @Excel(name = "提现手续费率[比例]")
    private BigDecimal withdrawalFeeRate;

    @Excel(name = "币种状态", readConverterExp = "0=禁用,1=启用")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCoinName(String coinName) 
    {
        this.coinName = coinName;
    }

    public String getCoinName() 
    {
        return coinName;
    }
    public void setCoinFullName(String coinFullName) 
    {
        this.coinFullName = coinFullName;
    }

    public String getCoinFullName() 
    {
        return coinFullName;
    }
    
    public BigDecimal getWithdrawalMin() {
        return withdrawalMin;
    }

    public void setWithdrawalMin(BigDecimal withdrawalMin) {
        this.withdrawalMin = withdrawalMin;
    }

    public BigDecimal getWithdrawalFee() {
        return withdrawalFee;
    }

    public void setWithdrawalFee(BigDecimal withdrawalFee) {
        this.withdrawalFee = withdrawalFee;
    }

    public BigDecimal getWithdrawalFeeRate() {
        return withdrawalFeeRate;
    }

    public void setWithdrawalFeeRate(BigDecimal withdrawalFeeRate) {
        this.withdrawalFeeRate = withdrawalFeeRate;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("coinName", getCoinName())
            .append("coinFullName", getCoinFullName())
            .append("withdrawalMin", getWithdrawalMin())
            .append("withdrawalFee", getWithdrawalFee())
            .append("withdrawalFeeRate", getWithdrawalFeeRate())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
