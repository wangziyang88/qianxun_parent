package com.qianxun.project.qxun.service.impl;

import java.util.List;

import com.qianxun.common.utils.DateUtils;
import com.qianxun.project.qxun.domain.QxGiftCfg;
import com.qianxun.project.qxun.mapper.QxGiftCfgMapper;
import com.qianxun.project.qxun.service.IQxGiftCfgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QxGiftCfgServiceImpl implements IQxGiftCfgService
{
    @Autowired
    private QxGiftCfgMapper qxGiftCfgMapper;

    @Override
    public QxGiftCfg selectQxGiftCfgById(Long id)
    {
        return qxGiftCfgMapper.selectQxGiftCfgById(id);
    }

    @Override
    public List<QxGiftCfg> selectQxGiftCfgList(QxGiftCfg qxGiftCfg)
    {
        return qxGiftCfgMapper.selectQxGiftCfgList(qxGiftCfg);
    }

    @Override
    public int insertQxGiftCfg(QxGiftCfg qxGiftCfg)
    {
        qxGiftCfg.setCreateTime(DateUtils.getNowDate());
        return qxGiftCfgMapper.insertQxGiftCfg(qxGiftCfg);
    }

    @Override
    public int updateQxGiftCfg(QxGiftCfg qxGiftCfg)
    {
        qxGiftCfg.setUpdateTime(DateUtils.getNowDate());
        return qxGiftCfgMapper.updateQxGiftCfg(qxGiftCfg);
    }

    @Override
    public int deleteQxGiftCfgByIds(Long[] ids)
    {
        return qxGiftCfgMapper.deleteQxGiftCfgByIds(ids);
    }

    @Override
    public int deleteQxGiftCfgById(Long id)
    {
        return qxGiftCfgMapper.deleteQxGiftCfgById(id);
    }
}
