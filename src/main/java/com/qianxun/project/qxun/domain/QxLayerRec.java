package com.qianxun.project.qxun.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.qianxun.framework.aspectj.lang.annotation.Excel;
import com.qianxun.framework.web.domain.BaseEntity;

public class QxLayerRec extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "用户ID")
    private Long userId;

    @Excel(name = "来自ID")
    private Long fromId;

    @Excel(name = "第几代")
    private Long era;

    @Excel(name = "分佣数量")
    private BigDecimal num;

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
    public void setFromId(Long fromId) 
    {
        this.fromId = fromId;
    }

    public Long getFromId() 
    {
        return fromId;
    }
    public void setEra(Long era) 
    {
        this.era = era;
    }

    public Long getEra() 
    {
        return era;
    }
    public void setNum(BigDecimal num) 
    {
        this.num = num;
    }

    public BigDecimal getNum() 
    {
        return num;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("fromId", getFromId())
            .append("era", getEra())
            .append("num", getNum())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
