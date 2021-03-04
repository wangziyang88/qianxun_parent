package com.qianxun.project.ucenter.service;

import com.qianxun.project.ucenter.domain.QianxunUserWechat;

public interface IQianxunUserWechatService {


    public int deleteByPrimaryKey(Integer id);


    public int insert(QianxunUserWechat record);


    public int insertSelective(QianxunUserWechat record);


    public QianxunUserWechat selectByPrimaryKey(Integer id);


    public int updateByPrimaryKeySelective(QianxunUserWechat record);


    public int updateByPrimaryKey(QianxunUserWechat record);


    public QianxunUserWechat selectByUserId(Long userId);
}
