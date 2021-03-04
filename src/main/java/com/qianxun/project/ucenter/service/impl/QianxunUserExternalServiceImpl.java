package com.qianxun.project.ucenter.service.impl;

import com.qianxun.project.ucenter.domain.QianxunUserAlipay;
import com.qianxun.project.ucenter.domain.QianxunUserExternal;
import com.qianxun.project.ucenter.mapper.QianxunUserExternalMapper;
import com.qianxun.project.ucenter.service.IQianxunUserExternalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:
 * @Email:
 * @Description:
 * @Date:2021/2/27 14:34
 * @Version:1.0
 */
@Service
public class QianxunUserExternalServiceImpl implements IQianxunUserExternalService {

    @Resource
    private QianxunUserExternalMapper qianxunUserExternalMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return qianxunUserExternalMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(QianxunUserExternal record) {
        return qianxunUserExternalMapper.insert(record);
    }

    @Override
    public int insertOrUpdate(QianxunUserExternal record) {
        return qianxunUserExternalMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(QianxunUserExternal record) {
        return qianxunUserExternalMapper.insertOrUpdateSelective(record);
    }

    @Override
    public int insertSelective(QianxunUserExternal record) {
        return qianxunUserExternalMapper.insertSelective(record);
    }

    @Override
    public QianxunUserExternal selectByPrimaryKey(Integer id) {
        return qianxunUserExternalMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(QianxunUserExternal record) {
        return qianxunUserExternalMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(QianxunUserExternal record) {
        return qianxunUserExternalMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateBatch(List<QianxunUserExternal> list) {
        return qianxunUserExternalMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<QianxunUserExternal> list) {
        return qianxunUserExternalMapper.batchInsert(list);
    }

    @Override
    public QianxunUserExternal selectByUserId(long userId) {
        return qianxunUserExternalMapper.selectByUserId(userId);
    }
}
