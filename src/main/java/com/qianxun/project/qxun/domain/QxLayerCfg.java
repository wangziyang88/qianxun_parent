package com.qianxun.project.qxun.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.qianxun.framework.aspectj.lang.annotation.Excel;
import com.qianxun.framework.web.domain.BaseEntity;

public class QxLayerCfg extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "代数")
    private Long era;

    @Excel(name = "条件 直推人数")
    private Long condition;

    @Excel(name = "分佣比例")
    private BigDecimal ratio;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setEra(Long era) 
    {
        this.era = era;
    }

    public Long getEra() 
    {
        return era;
    }
    public void setCondition(Long condition) 
    {
        this.condition = condition;
    }

    public Long getCondition() 
    {
        return condition;
    }
    public void setRatio(BigDecimal ratio) 
    {
        this.ratio = ratio;
    }

    public BigDecimal getRatio() 
    {
        return ratio;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("era", getEra())
            .append("condition", getCondition())
            .append("ratio", getRatio())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
