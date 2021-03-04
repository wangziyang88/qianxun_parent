package com.qianxun.project.ucenter.service.impl;

import com.qianxun.project.ucenter.domain.QianxunUserBank;
import com.qianxun.project.ucenter.domain.QianxunUserExternal;
import com.qianxun.project.ucenter.mapper.QianxunUserBankMapper;
import com.qianxun.project.ucenter.service.IQianxunUserBankService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QianxunUserBankService implements IQianxunUserBankService {

    @Resource
    private QianxunUserBankMapper qianxunUserBankMapper;

    
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return qianxunUserBankMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(QianxunUserBank record) {
        return qianxunUserBankMapper.insert(record);
    }

    @Override
    public int insertOrUpdate(QianxunUserBank record) {
        return qianxunUserBankMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(QianxunUserBank record) {
        return qianxunUserBankMapper.insertOrUpdateSelective(record);
    }

    @Override
    public int insertSelective(QianxunUserBank record) {
        return qianxunUserBankMapper.insertSelective(record);
    }

    @Override
    public QianxunUserBank selectByPrimaryKey(Integer id) {
        return qianxunUserBankMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(QianxunUserBank record) {
        return qianxunUserBankMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(QianxunUserBank record) {
        return qianxunUserBankMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateBatch(List<QianxunUserBank> list) {
        return qianxunUserBankMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<QianxunUserBank> list) {
        return qianxunUserBankMapper.batchInsert(list);
    }

    @Override
    public QianxunUserBank selectByUserId(long userId) {
        return qianxunUserBankMapper.selectByUserId(userId);
    }

}
