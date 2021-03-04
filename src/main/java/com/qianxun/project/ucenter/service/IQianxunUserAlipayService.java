package com.qianxun.project.ucenter.service;

import com.qianxun.project.ucenter.domain.QianxunUserAlipay;


public interface IQianxunUserAlipayService {


    public int deleteByPrimaryKey(Integer id);

    public int insert(QianxunUserAlipay record);

    public int insertSelective(QianxunUserAlipay record);

    public QianxunUserAlipay selectByPrimaryKey(Integer id);

    public int updateByPrimaryKeySelective(QianxunUserAlipay record);

    public int updateByPrimaryKey(QianxunUserAlipay record);

    QianxunUserAlipay selectByUserId(long userId);
}

