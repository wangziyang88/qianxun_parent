package com.qianxun.project.qxun.mapper;

import java.util.List;
import com.qianxun.project.qxun.domain.QxShareRec;

public interface QxShareRecMapper 
{
    public QxShareRec selectQxShareRecById(Long id);

    public List<QxShareRec> selectQxShareRecList(QxShareRec qxShareRec);

    public int insertQxShareRec(QxShareRec qxShareRec);

    public int updateQxShareRec(QxShareRec qxShareRec);

    public int deleteQxShareRecById(Long id);

    public int deleteQxShareRecByIds(Long[] ids);
}
