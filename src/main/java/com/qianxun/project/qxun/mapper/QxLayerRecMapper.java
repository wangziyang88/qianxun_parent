package com.qianxun.project.qxun.mapper;

import java.util.List;
import com.qianxun.project.qxun.domain.QxLayerRec;

public interface QxLayerRecMapper 
{
    public QxLayerRec selectQxLayerRecById(Long id);

    public List<QxLayerRec> selectQxLayerRecList(QxLayerRec qxLayerRec);

    public int insertQxLayerRec(QxLayerRec qxLayerRec);

    public int updateQxLayerRec(QxLayerRec qxLayerRec);

    public int deleteQxLayerRecById(Long id);

    public int deleteQxLayerRecByIds(Long[] ids);
}
