package com.qianxun.project.qxun.service.impl;

import java.util.List;
import com.qianxun.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qianxun.project.qxun.mapper.QxVipRecMapper;
import com.qianxun.project.qxun.domain.QxVipRec;
import com.qianxun.project.qxun.service.IQxVipRecService;

@Service
public class QxVipRecServiceImpl implements IQxVipRecService 
{
    @Autowired
    private QxVipRecMapper qxVipRecMapper;

    @Override
    public QxVipRec selectQxVipRecById(Long id)
    {
        return qxVipRecMapper.selectQxVipRecById(id);
    }

    @Override
    public List<QxVipRec> selectQxVipRecList(QxVipRec qxVipRec)
    {
        return qxVipRecMapper.selectQxVipRecList(qxVipRec);
    }

    @Override
    public int insertQxVipRec(QxVipRec qxVipRec)
    {
        qxVipRec.setCreateTime(DateUtils.getNowDate());
        return qxVipRecMapper.insertQxVipRec(qxVipRec);
    }

    @Override
    public int updateQxVipRec(QxVipRec qxVipRec)
    {
        qxVipRec.setUpdateTime(DateUtils.getNowDate());
        return qxVipRecMapper.updateQxVipRec(qxVipRec);
    }

    @Override
    public int deleteQxVipRecByIds(Long[] ids)
    {
        return qxVipRecMapper.deleteQxVipRecByIds(ids);
    }

    @Override
    public int deleteQxVipRecById(Long id)
    {
        return qxVipRecMapper.deleteQxVipRecById(id);
    }
}
