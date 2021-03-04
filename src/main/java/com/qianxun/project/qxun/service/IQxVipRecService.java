package com.qianxun.project.qxun.service;

import java.util.List;
import com.qianxun.project.qxun.domain.QxVipRec;

public interface IQxVipRecService 
{
    public QxVipRec selectQxVipRecById(Long id);

    public List<QxVipRec> selectQxVipRecList(QxVipRec qxVipRec);

    public int insertQxVipRec(QxVipRec qxVipRec);

    public int updateQxVipRec(QxVipRec qxVipRec);

    public int deleteQxVipRecByIds(Long[] ids);

    public int deleteQxVipRecById(Long id);
}
