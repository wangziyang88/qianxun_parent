package com.qianxun.project.qxun.service;

import java.util.List;
import com.qianxun.project.qxun.domain.QxLayerRec;

public interface IQxLayerRecService 
{
    public QxLayerRec selectQxLayerRecById(Long id);

    public List<QxLayerRec> selectQxLayerRecList(QxLayerRec qxLayerRec);

    public int insertQxLayerRec(QxLayerRec qxLayerRec);

    public int updateQxLayerRec(QxLayerRec qxLayerRec);

    public int deleteQxLayerRecByIds(Long[] ids);

    public int deleteQxLayerRecById(Long id);
}
