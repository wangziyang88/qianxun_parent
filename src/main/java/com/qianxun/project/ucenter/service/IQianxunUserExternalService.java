package com.qianxun.project.ucenter.service;

import com.qianxun.project.ucenter.domain.QianxunUserAlipay;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qianxun.project.ucenter.mapper.QianxunUserExternalMapper;
import java.util.List;
import com.qianxun.project.ucenter.domain.QianxunUserExternal;

public interface IQianxunUserExternalService{

    public int deleteByPrimaryKey(Integer id);

    
    public int insert(QianxunUserExternal record);

    
    public int insertOrUpdate(QianxunUserExternal record);

    
    public int insertOrUpdateSelective(QianxunUserExternal record);

    
    public int insertSelective(QianxunUserExternal record);

    
    public QianxunUserExternal selectByPrimaryKey(Integer id);

    public int updateByPrimaryKeySelective(QianxunUserExternal record);

    
    public int updateByPrimaryKey(QianxunUserExternal record);

    
    public int updateBatch(List<QianxunUserExternal> list);

    
    public int batchInsert(List<QianxunUserExternal> list);

    QianxunUserExternal selectByUserId(long userId);
}
