package com.qianxun.project.ucenter.mapper;

import com.qianxun.project.ucenter.domain.QianxunUserAlipay;

public interface QianxunUserAlipayMapper {
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
    int insert(QianxunUserAlipay record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(QianxunUserAlipay record);

    /**
     * select by userId
     * @param userId primary key
     * @return object by primary key
     */
    QianxunUserAlipay selectByUserId(Long userId);


    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    QianxunUserAlipay selectByPrimaryKey(Integer id);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(QianxunUserAlipay record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(QianxunUserAlipay record);
}