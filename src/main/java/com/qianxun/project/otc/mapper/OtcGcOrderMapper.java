package com.qianxun.project.otc.mapper;

import com.qianxun.project.otc.domain.OtcGcOrder;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OtcGcOrderMapper {
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
    int insert(OtcGcOrder record);

    int insertOrUpdate(OtcGcOrder record);

    int insertOrUpdateSelective(OtcGcOrder record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(OtcGcOrder record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    OtcGcOrder selectByPrimaryKey(Integer id);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OtcGcOrder record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OtcGcOrder record);

    int updateBatch(List<OtcGcOrder> list);

    int batchInsert(@Param("list") List<OtcGcOrder> list);

    /**
     * 条件查询订单列表
     *
     * @param otcGcOrder
     * @return
     */
    List<OtcGcOrder> selectOtcGcOrderList(OtcGcOrder otcGcOrder);
}