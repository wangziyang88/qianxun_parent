package com.qianxun.project.qxun.service;

import java.util.List;
import com.qianxun.project.qxun.domain.QxShareRec;

public interface IQxShareRecService 
{
    public QxShareRec selectQxShareRecById(Long id);

    public List<QxShareRec> selectQxShareRecList(QxShareRec qxShareRec);

    public int insertQxShareRec(QxShareRec qxShareRec);

    public int updateQxShareRec(QxShareRec qxShareRec);

    public int deleteQxShareRecByIds(Long[] ids);

    public int deleteQxShareRecById(Long id);
}
