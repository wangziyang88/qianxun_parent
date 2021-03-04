package com.qianxun.project.qxun.service.impl;

import java.util.List;
import com.qianxun.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qianxun.project.qxun.mapper.QxLayerRecMapper;
import com.qianxun.project.qxun.domain.QxLayerRec;
import com.qianxun.project.qxun.service.IQxLayerRecService;

@Service
public class QxLayerRecServiceImpl implements IQxLayerRecService 
{
    @Autowired
    private QxLayerRecMapper qxLayerRecMapper;

    @Override
    public QxLayerRec selectQxLayerRecById(Long id)
    {
        return qxLayerRecMapper.selectQxLayerRecById(id);
    }

    @Override
    public List<QxLayerRec> selectQxLayerRecList(QxLayerRec qxLayerRec)
    {
        return qxLayerRecMapper.selectQxLayerRecList(qxLayerRec);
    }

    @Override
    public int insertQxLayerRec(QxLayerRec qxLayerRec)
    {
        qxLayerRec.setCreateTime(DateUtils.getNowDate());
        return qxLayerRecMapper.insertQxLayerRec(qxLayerRec);
    }

    @Override
    public int updateQxLayerRec(QxLayerRec qxLayerRec)
    {
        qxLayerRec.setUpdateTime(DateUtils.getNowDate());
        return qxLayerRecMapper.updateQxLayerRec(qxLayerRec);
    }

    @Override
    public int deleteQxLayerRecByIds(Long[] ids)
    {
        return qxLayerRecMapper.deleteQxLayerRecByIds(ids);
    }

    @Override
    public int deleteQxLayerRecById(Long id)
    {
        return qxLayerRecMapper.deleteQxLayerRecById(id);
    }
}
