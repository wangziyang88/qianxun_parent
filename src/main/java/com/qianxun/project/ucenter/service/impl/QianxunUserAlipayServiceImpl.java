package com.qianxun.project.ucenter.service.impl;

import com.qianxun.common.utils.DateUtils;
import com.qianxun.project.ucenter.domain.QianxunUserAlipay;
import com.qianxun.project.ucenter.mapper.QianxunUserAlipayMapper;
import com.qianxun.project.ucenter.service.IQianxunUserAlipayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class QianxunUserAlipayServiceImpl implements IQianxunUserAlipayService {

    @Resource
    private QianxunUserAlipayMapper qianxunUserAlipayMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return qianxunUserAlipayMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(QianxunUserAlipay record) {
        return qianxunUserAlipayMapper.insert(record);
    }

    @Override
    public int insertSelective(QianxunUserAlipay record) {
        return qianxunUserAlipayMapper.insertSelective(record);
    }

    @Override
    public QianxunUserAlipay selectByPrimaryKey(Integer id) {
        return qianxunUserAlipayMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(QianxunUserAlipay record) {
        return qianxunUserAlipayMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(QianxunUserAlipay record) {
        record.setUpdateTime(DateUtils.getNowDate());
        return qianxunUserAlipayMapper.updateByPrimaryKey(record);
    }

    @Override
    public QianxunUserAlipay selectByUserId(long userId) {
        return qianxunUserAlipayMapper.selectByUserId(userId);
    }
}

