package com.qianxun.project.qxun.service.impl;

import java.util.List;
import com.qianxun.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qianxun.project.qxun.mapper.QxLayerCfgMapper;
import com.qianxun.project.qxun.domain.QxLayerCfg;
import com.qianxun.project.qxun.service.IQxLayerCfgService;

@Service
public class QxLayerCfgServiceImpl implements IQxLayerCfgService 
{
    @Autowired
    private QxLayerCfgMapper qxLayerCfgMapper;

    @Override
    public QxLayerCfg selectQxLayerCfgById(Long id)
    {
        return qxLayerCfgMapper.selectQxLayerCfgById(id);
    }

    @Override
    public List<QxLayerCfg> selectQxLayerCfgList(QxLayerCfg qxLayerCfg)
    {
        return qxLayerCfgMapper.selectQxLayerCfgList(qxLayerCfg);
    }

    @Override
    public int insertQxLayerCfg(QxLayerCfg qxLayerCfg)
    {
        qxLayerCfg.setCreateTime(DateUtils.getNowDate());
        return qxLayerCfgMapper.insertQxLayerCfg(qxLayerCfg);
    }

    @Override
    public int updateQxLayerCfg(QxLayerCfg qxLayerCfg)
    {
        qxLayerCfg.setUpdateTime(DateUtils.getNowDate());
        return qxLayerCfgMapper.updateQxLayerCfg(qxLayerCfg);
    }

    @Override
    public int deleteQxLayerCfgByIds(Long[] ids)
    {
        return qxLayerCfgMapper.deleteQxLayerCfgByIds(ids);
    }

    @Override
    public int deleteQxLayerCfgById(Long id)
    {
        return qxLayerCfgMapper.deleteQxLayerCfgById(id);
    }
}
