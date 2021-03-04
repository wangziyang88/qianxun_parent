package com.qianxun.project.qxun.domain;

import com.qianxun.framework.aspectj.lang.annotation.Excel;
import com.qianxun.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class QxUserAcc extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "用户ID")
    private Long userId;

    @Excel(name = "礼包等级", readConverterExp = "1=铜级,2=银级,3=金级")
    private Long giftGrade;

    @Excel(name = "VIP等级")
    private Long vipGrade;

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
    public void setGiftGrade(Long giftGrade) 
    {
        this.giftGrade = giftGrade;
    }

    public Long getGiftGrade() 
    {
        return giftGrade;
    }
    public void setVipGrade(Long vipGrade) 
    {
        this.vipGrade = vipGrade;
    }

    public Long getVipGrade() 
    {
        return vipGrade;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("giftGrade", getGiftGrade())
            .append("vipGrade", getVipGrade())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
