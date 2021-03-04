package com.qianxun.project.qxun.service.impl;

import java.util.List;

import com.qianxun.common.utils.DateUtils;
import com.qianxun.project.qxun.domain.QxUserAcc;
import com.qianxun.project.qxun.mapper.QxUserAccMapper;
import com.qianxun.project.qxun.service.IQxUserAccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QxUserAccServiceImpl implements IQxUserAccService
{
    @Autowired
    private QxUserAccMapper qxUserAccMapper;

    @Override
    public QxUserAcc selectQxUserAccById(Long id)
    {
        return qxUserAccMapper.selectQxUserAccById(id);
    }

    @Override
    public List<QxUserAcc> selectQxUserAccList(QxUserAcc qxUserAcc)
    {
        return qxUserAccMapper.selectQxUserAccList(qxUserAcc);
    }

    @Override
    public int insertQxUserAcc(QxUserAcc qxUserAcc)
    {
        qxUserAcc.setCreateTime(DateUtils.getNowDate());
        return qxUserAccMapper.insertQxUserAcc(qxUserAcc);
    }

    @Override
    public int updateQxUserAcc(QxUserAcc qxUserAcc)
    {
        qxUserAcc.setUpdateTime(DateUtils.getNowDate());
        return qxUserAccMapper.updateQxUserAcc(qxUserAcc);
    }

    @Override
    public int deleteQxUserAccByIds(Long[] ids)
    {
        return qxUserAccMapper.deleteQxUserAccByIds(ids);
    }

    @Override
    public int deleteQxUserAccById(Long id)
    {
        return qxUserAccMapper.deleteQxUserAccById(id);
    }

    @Override
    public QxUserAcc selectQxUserAccByUserId(long userId) {
        return qxUserAccMapper.selectQxUserAccByUserId(userId);
    }
}
