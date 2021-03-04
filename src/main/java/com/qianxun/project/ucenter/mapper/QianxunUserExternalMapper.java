package com.qianxun.project.ucenter.mapper;

import com.qianxun.project.ucenter.domain.QianxunUserExternal;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QianxunUserExternalMapper {
    /**
     * delete by primary key
     *
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(QianxunUserExternal record);

    int insertOrUpdate(QianxunUserExternal record);

    int insertOrUpdateSelective(QianxunUserExternal record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(QianxunUserExternal record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    QianxunUserExternal selectByPrimaryKey(Integer id);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(QianxunUserExternal record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(QianxunUserExternal record);

    int updateBatch(List<QianxunUserExternal> list);

    int batchInsert(@Param("list") List<QianxunUserExternal> list);



    QianxunUserExternal selectByUserId(long userId);
}