package com.qianxun.project.qxun.service.impl;

import java.util.List;
import com.qianxun.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qianxun.project.qxun.mapper.QxVipCfgMapper;
import com.qianxun.project.qxun.domain.QxVipCfg;
import com.qianxun.project.qxun.service.IQxVipCfgService;

@Service
public class QxVipCfgServiceImpl implements IQxVipCfgService 
{
    @Autowired
    private QxVipCfgMapper qxVipCfgMapper;

    @Override
    public QxVipCfg selectQxVipCfgById(Long id)
    {
        return qxVipCfgMapper.selectQxVipCfgById(id);
    }

    @Override
    public List<QxVipCfg> selectQxVipCfgList(QxVipCfg qxVipCfg)
    {
        return qxVipCfgMapper.selectQxVipCfgList(qxVipCfg);
    }

    @Override
    public int insertQxVipCfg(QxVipCfg qxVipCfg)
    {
        qxVipCfg.setCreateTime(DateUtils.getNowDate());
        return qxVipCfgMapper.insertQxVipCfg(qxVipCfg);
    }

    @Override
    public int updateQxVipCfg(QxVipCfg qxVipCfg)
    {
        qxVipCfg.setUpdateTime(DateUtils.getNowDate());
        return qxVipCfgMapper.updateQxVipCfg(qxVipCfg);
    }

    @Override
    public int deleteQxVipCfgByIds(Long[] ids)
    {
        return qxVipCfgMapper.deleteQxVipCfgByIds(ids);
    }

    @Override
    public int deleteQxVipCfgById(Long id)
    {
        return qxVipCfgMapper.deleteQxVipCfgById(id);
    }
}
