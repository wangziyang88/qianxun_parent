package com.qianxun.project.coin.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qianxun.framework.aspectj.lang.annotation.Excel;
import com.qianxun.framework.config.SerializerBigDecimal;
import com.qianxun.framework.web.domain.BaseEntity;

public class CoinAccount extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @Excel(name = "ID")
    private Long id;

    @Excel(name = "用户ID")
    private Long userId;

    @Excel(name = "币种ID")
    private Long coinId;

    @Excel(name = "币种名")
    private String coinName;

    @JsonSerialize(using = SerializerBigDecimal.class)
    @Excel(name = "余额")
    private BigDecimal available;

    @JsonSerialize(using = SerializerBigDecimal.class)
    @Excel(name = "冻结")
    private BigDecimal frozen;

    @JsonSerialize(using = SerializerBigDecimal.class)
    @Excel(name = "锁仓")
    private BigDecimal lockup;

    @Excel(name = "版本号")
    private Long version;

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
    public void setAvailable(BigDecimal available) 
    {
        this.available = available;
    }

    public BigDecimal getAvailable() 
    {
        return available;
    }
    public void setFrozen(BigDecimal frozen) 
    {
        this.frozen = frozen;
    }

    public BigDecimal getFrozen() 
    {
        return frozen;
    }
    public void setLockup(BigDecimal lockup) 
    {
        this.lockup = lockup;
    }

    public BigDecimal getLockup() 
    {
        return lockup;
    }
    public void setVersion(Long version) 
    {
        this.version = version;
    }

    public Long getVersion() 
    {
        return version;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("coinId", getCoinId())
            .append("coinName", getCoinName())
            .append("available", getAvailable())
            .append("frozen", getFrozen())
            .append("lockup", getLockup())
            .append("version", getVersion())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
