package com.qianxun.project.qxun.mapper;

import java.util.List;
import com.qianxun.project.qxun.domain.QxVipRec;

public interface QxVipRecMapper 
{
    public QxVipRec selectQxVipRecById(Long id);

    public List<QxVipRec> selectQxVipRecList(QxVipRec qxVipRec);

    public int insertQxVipRec(QxVipRec qxVipRec);

    public int updateQxVipRec(QxVipRec qxVipRec);

    public int deleteQxVipRecById(Long id);

    public int deleteQxVipRecByIds(Long[] ids);
}
