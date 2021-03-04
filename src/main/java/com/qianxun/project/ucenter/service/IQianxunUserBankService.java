package com.qianxun.project.ucenter.service;

import com.qianxun.project.ucenter.domain.QianxunUserExternal;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.qianxun.project.ucenter.mapper.QianxunUserBankMapper;
import com.qianxun.project.ucenter.domain.QianxunUserBank;
public interface IQianxunUserBankService{
    
    public int deleteByPrimaryKey(Integer id);

    
    public int insert(QianxunUserBank record);

    
    public int insertOrUpdate(QianxunUserBank record);

    
    public int insertOrUpdateSelective(QianxunUserBank record);

    
    public int insertSelective(QianxunUserBank record);

    
    public QianxunUserBank selectByPrimaryKey(Integer id);

    
    public int updateByPrimaryKeySelective(QianxunUserBank record);

    
    public int updateByPrimaryKey(QianxunUserBank record);

    
    public int updateBatch(List<QianxunUserBank> list);

    
    public int batchInsert(List<QianxunUserBank> list);

    QianxunUserBank selectByUserId(long userId);
}
