package com.qianxun.project.ucenter.mapper;

import com.qianxun.project.ucenter.domain.QianxunUserBank;
import java.util.List;

import com.qianxun.project.ucenter.domain.QianxunUserExternal;
import org.apache.ibatis.annotations.Param;

public interface QianxunUserBankMapper {
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
    int insert(QianxunUserBank record);

    int insertOrUpdate(QianxunUserBank record);

    int insertOrUpdateSelective(QianxunUserBank record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(QianxunUserBank record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    QianxunUserBank selectByPrimaryKey(Integer id);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(QianxunUserBank record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(QianxunUserBank record);

    int updateBatch(List<QianxunUserBank> list);

    int batchInsert(@Param("list") List<QianxunUserBank> list);

    QianxunUserBank selectByUserId(long userId);
}