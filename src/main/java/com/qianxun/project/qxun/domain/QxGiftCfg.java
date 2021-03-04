package com.qianxun.project.qxun.domain;

import java.math.BigDecimal;

import com.qianxun.framework.aspectj.lang.annotation.Excel;
import com.qianxun.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class QxGiftCfg extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "等级")
    private String grade;

    @Excel(name = "GC数量")
    private BigDecimal gcNum;

    @Excel(name = "ET比例")
    private BigDecimal etRatio;

    @Excel(name = "推1人比例")
    private BigDecimal rec1;

    @Excel(name = "推3人比例")
    private BigDecimal rec3;

    @Excel(name = "推7人比例")
    private BigDecimal rec7;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setGrade(String grade) 
    {
        this.grade = grade;
    }

    public String getGrade() 
    {
        return grade;
    }
    public void setGcNum(BigDecimal gcNum) 
    {
        this.gcNum = gcNum;
    }

    public BigDecimal getGcNum() 
    {
        return gcNum;
    }
    public void setEtRatio(BigDecimal etRatio) 
    {
        this.etRatio = etRatio;
    }

    public BigDecimal getEtRatio() 
    {
        return etRatio;
    }
    public void setRec1(BigDecimal rec1) 
    {
        this.rec1 = rec1;
    }

    public BigDecimal getRec1() 
    {
        return rec1;
    }
    public void setRec3(BigDecimal rec3) 
    {
        this.rec3 = rec3;
    }

    public BigDecimal getRec3() 
    {
        return rec3;
    }
    public void setRec7(BigDecimal rec7) 
    {
        this.rec7 = rec7;
    }

    public BigDecimal getRec7() 
    {
        return rec7;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("grade", getGrade())
            .append("gcNum", getGcNum())
            .append("etRatio", getEtRatio())
            .append("rec1", getRec1())
            .append("rec3", getRec3())
            .append("rec7", getRec7())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
