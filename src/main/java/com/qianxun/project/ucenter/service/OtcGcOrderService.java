package com.qianxun.project.ucenter.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.qianxun.project.otc.mapper.OtcGcOrderMapper;
import com.qianxun.project.otc.domain.OtcGcOrder;

@Service
public class OtcGcOrderService {

    @Resource
    private OtcGcOrderMapper otcGcOrderMapper;


    public int deleteByPrimaryKey(Integer id) {
        return otcGcOrderMapper.deleteByPrimaryKey(id);
    }


    public int insert(OtcGcOrder record) {
        return otcGcOrderMapper.insert(record);
    }


    public int insertOrUpdate(OtcGcOrder record) {
        return otcGcOrderMapper.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(OtcGcOrder record) {
        return otcGcOrderMapper.insertOrUpdateSelective(record);
    }


    public int insertSelective(OtcGcOrder record) {
        return otcGcOrderMapper.insertSelective(record);
    }


    public OtcGcOrder selectByPrimaryKey(Integer id) {
        return otcGcOrderMapper.selectByPrimaryKey(id);
    }


    public int updateByPrimaryKeySelective(OtcGcOrder record) {
        return otcGcOrderMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(OtcGcOrder record) {
        return otcGcOrderMapper.updateByPrimaryKey(record);
    }


    public int updateBatch(List<OtcGcOrder> list) {
        return otcGcOrderMapper.updateBatch(list);
    }


    public int batchInsert(List<OtcGcOrder> list) {
        return otcGcOrderMapper.batchInsert(list);
    }

}




