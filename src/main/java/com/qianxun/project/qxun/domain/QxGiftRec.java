package com.qianxun.project.qxun.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.qianxun.framework.aspectj.lang.annotation.Excel;
import com.qianxun.framework.web.domain.BaseEntity;

public class QxGiftRec extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "用户ID")
    private Long userId;

    @Excel(name = "礼包等级", readConverterExp = "1=铜级,2=银级,3=金级")
    private Long giftLevel;

    @Excel(name = "GC数量")
    private BigDecimal gcNum;
    
    @Excel(name = "ET数量")
    private BigDecimal etNum;

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
    public void setGiftLevel(Long giftLevel) 
    {
        this.giftLevel = giftLevel;
    }

    public Long getGiftLevel() 
    {
        return giftLevel;
    }
    public void setGcNum(BigDecimal gcNum) 
    {
        this.gcNum = gcNum;
    }

    public BigDecimal getGcNum() 
    {
        return gcNum;
    }
    
    public BigDecimal getEtNum() {
        return etNum;
    }

    public void setEtNum(BigDecimal etNum) {
        this.etNum = etNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("giftLevel", getGiftLevel())
            .append("gcNum", getGcNum())
            .append("etNum", getEtNum())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
