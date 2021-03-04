package com.qianxun.project.coin.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qianxun.framework.aspectj.lang.annotation.Excel;
import com.qianxun.framework.config.SerializerBigDecimal;
import com.qianxun.framework.web.domain.BaseEntity;

public class CoinRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "用户ID")
    private Long userId;

    @Excel(name = "到达用户ID")
    private Long toId;

    @Excel(name = "币种ID")
    private Long coinId;

    @Excel(name = "币种名")
    private String coinName;

    @Excel(name = "收支类型", readConverterExp = "1=收入,2=支出")
    private String incomeType;

    @Excel(name = "主类型")
    private String mainType;

    @Excel(name = "子类型")
    private String sonType;

    @JsonSerialize(using = SerializerBigDecimal.class)
    @Excel(name = "数量")
    private BigDecimal amount;

    @Excel(name = "来自地址")
    private String fromAddress;

    @Excel(name = "到达地址")
    private String toAddress;

    @Excel(name = "交易ID")
    private String txid;
    
    @Excel(name = "备注")
    private String memo;

    @JsonSerialize(using = SerializerBigDecimal.class)
    @Excel(name = "手续费")
    private BigDecimal fee;

    @Excel(name = "状态", readConverterExp = "1=成功,2=待审核,3=审核通过,4=审核拒绝")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setToId(Long toId) 
    {
        this.toId = toId;
    }

    public Long getToId() 
    {
        return toId;
    }
    public void setCoinId(Long coinId) 
    {
        this.coinId = coinId;
    }

    public Long getCoinId() 
    {
        return coinId;
    }
    public void setCoinName(String coinName) 
    {
        this.coinName = coinName;
    }

    public String getCoinName() 
    {
        return coinName;
    }
    public void setIncomeType(String incomeType) 
    {
        this.incomeType = incomeType;
    }

    public String getIncomeType() 
    {
        return incomeType;
    }
    public void setMainType(String mainType) 
    {
        this.mainType = mainType;
    }

    public String getMainType() 
    {
        return mainType;
    }
    public void setSonType(String sonType) 
    {
        this.sonType = sonType;
    }

    public String getSonType() 
    {
        return sonType;
    }
    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }
    public void setFromAddress(String fromAddress) 
    {
        this.fromAddress = fromAddress;
    }

    public String getFromAddress() 
    {
        return fromAddress;
    }
    public void setToAddress(String toAddress) 
    {
        this.toAddress = toAddress;
    }

    public String getToAddress() 
    {
        return toAddress;
    }
    public void setTxid(String txid) 
    {
        this.txid = txid;
    }

    public String getTxid() 
    {
        return txid;
    }
    
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setFee(BigDecimal fee) 
    {
        this.fee = fee;
    }

    public BigDecimal getFee() 
    {
        return fee;
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
            .append("userId", getUserId())
            .append("toId", getToId())
            .append("coinId", getCoinId())
            .append("coinName", getCoinName())
            .append("incomeType", getIncomeType())
            .append("mainType", getMainType())
            .append("sonType", getSonType())
            .append("amount", getAmount())
            .append("fromAddress", getFromAddress())
            .append("toAddress", getToAddress())
            .append("txid", getTxid())
            .append("memo", getMemo())
            .append("fee", getFee())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
