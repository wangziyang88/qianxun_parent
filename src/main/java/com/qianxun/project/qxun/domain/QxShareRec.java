package com.qianxun.project.qxun.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.qianxun.framework.aspectj.lang.annotation.Excel;
import com.qianxun.framework.web.domain.BaseEntity;

public class QxShareRec extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "用户ID")
    private Long userId;

    @Excel(name = "用户当前等级")
    private Long userGrade;

    @Excel(name = "来自ID")
    private Long fromId;

    @Excel(name = "代数比例")
    private BigDecimal ratio;

    @Excel(name = "实际分佣比例")
    private BigDecimal actualRatio;

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
    public void setUserGrade(Long userGrade) 
    {
        this.userGrade = userGrade;
    }

    public Long getUserGrade() 
    {
        return userGrade;
    }
    public void setFromId(Long fromId) 
    {
        this.fromId = fromId;
    }

    public Long getFromId() 
    {
        return fromId;
    }
    public void setRatio(BigDecimal ratio) 
    {
        this.ratio = ratio;
    }

    public BigDecimal getRatio() 
    {
        return ratio;
    }
    public void setActualRatio(BigDecimal actualRatio) 
    {
        this.actualRatio = actualRatio;
    }

    public BigDecimal getActualRatio() 
    {
        return actualRatio;
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
            .append("userGrade", getUserGrade())
            .append("fromId", getFromId())
            .append("ratio", getRatio())
            .append("actualRatio", getActualRatio())
            .append("era", getEra())
            .append("num", getNum())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
