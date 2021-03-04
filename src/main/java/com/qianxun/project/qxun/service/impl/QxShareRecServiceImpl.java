package com.qianxun.project.qxun.service.impl;

import java.util.List;
import com.qianxun.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qianxun.project.qxun.mapper.QxShareRecMapper;
import com.qianxun.project.qxun.domain.QxShareRec;
import com.qianxun.project.qxun.service.IQxShareRecService;

@Service
public class QxShareRecServiceImpl implements IQxShareRecService 
{
    @Autowired
    private QxShareRecMapper qxShareRecMapper;

    @Override
    public QxShareRec selectQxShareRecById(Long id)
    {
        return qxShareRecMapper.selectQxShareRecById(id);
    }

    @Override
    public List<QxShareRec> selectQxShareRecList(QxShareRec qxShareRec)
    {
        return qxShareRecMapper.selectQxShareRecList(qxShareRec);
    }

    @Override
    public int insertQxShareRec(QxShareRec qxShareRec)
    {
        qxShareRec.setCreateTime(DateUtils.getNowDate());
        return qxShareRecMapper.insertQxShareRec(qxShareRec);
    }

    @Override
    public int updateQxShareRec(QxShareRec qxShareRec)
    {
        qxShareRec.setUpdateTime(DateUtils.getNowDate());
        return qxShareRecMapper.updateQxShareRec(qxShareRec);
    }

    @Override
    public int deleteQxShareRecByIds(Long[] ids)
    {
        return qxShareRecMapper.deleteQxShareRecByIds(ids);
    }

    @Override
    public int deleteQxShareRecById(Long id)
    {
        return qxShareRecMapper.deleteQxShareRecById(id);
    }
}
