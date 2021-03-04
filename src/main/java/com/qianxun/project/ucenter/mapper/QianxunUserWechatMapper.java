package com.qianxun.project.ucenter.mapper;

import com.qianxun.project.ucenter.domain.QianxunUserWechat;

import java.util.List;

public interface QianxunUserWechatMapper {
    /**
     * delete by primary key
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(QianxunUserWechat record);


    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(QianxunUserWechat record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    QianxunUserWechat selectByPrimaryKey(Integer id);



    List<QianxunUserWechat> selectUserWechatList(QianxunUserWechat qianxunUserWechat);

    /**
     * select by userId
     * @param userId primary key
     * @return object by primary key
     */
    QianxunUserWechat selectByUserId(Long userId);


    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(QianxunUserWechat record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(QianxunUserWechat record);
}