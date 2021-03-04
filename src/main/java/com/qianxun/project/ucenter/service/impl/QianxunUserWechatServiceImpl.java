package com.qianxun.project.ucenter.service.impl;

import com.qianxun.common.utils.DateUtils;
import com.qianxun.project.ucenter.service.IQianxunUserWechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qianxun.project.ucenter.domain.QianxunUserWechat;
import com.qianxun.project.ucenter.mapper.QianxunUserWechatMapper;
@Service
public class QianxunUserWechatServiceImpl implements IQianxunUserWechatService {

    @Autowired
    private QianxunUserWechatMapper qianxunUserWechatMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return qianxunUserWechatMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(QianxunUserWechat record) {
        return qianxunUserWechatMapper.insert(record);
    }

    
    @Override
    public int insertSelective(QianxunUserWechat record) {
        return qianxunUserWechatMapper.insertSelective(record);
    }

    
    @Override
    public QianxunUserWechat selectByPrimaryKey(Integer id) {
        return qianxunUserWechatMapper.selectByPrimaryKey(id);
    }

    
    @Override
    public int updateByPrimaryKeySelective(QianxunUserWechat record) {
        return qianxunUserWechatMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(QianxunUserWechat record) {
        record.setUpdateTime(DateUtils.getNowDate());
        return qianxunUserWechatMapper.updateByPrimaryKey(record);
    }

    @Override
    public QianxunUserWechat selectByUserId(Long userId){
        return qianxunUserWechatMapper.selectByUserId(userId);
    }


}
