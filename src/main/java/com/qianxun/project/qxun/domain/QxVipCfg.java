package com.qianxun.project.qxun.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.qianxun.framework.aspectj.lang.annotation.Excel;
import com.qianxun.framework.web.domain.BaseEntity;

public class QxVipCfg extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "VIP等级")
    private String vipGrade;

    @Excel(name = "分佣比例")
    private BigDecimal ratio;

    @Excel(name = "总业绩条件")
    private BigDecimal condition;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setVipGrade(String vipGrade) 
    {
        this.vipGrade = vipGrade;
    }

    public String getVipGrade() 
    {
        return vipGrade;
    }
    public void setRatio(BigDecimal ratio) 
    {
        this.ratio = ratio;
    }

    public BigDecimal getRatio() 
    {
        return ratio;
    }
    public void setCondition(BigDecimal condition) 
    {
        this.condition = condition;
    }

    public BigDecimal getCondition() 
    {
        return condition;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("vipGrade", getVipGrade())
            .append("ratio", getRatio())
            .append("condition", getCondition())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
