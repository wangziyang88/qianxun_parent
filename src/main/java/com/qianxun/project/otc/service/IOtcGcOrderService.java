package com.qianxun.project.otc.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.qianxun.project.otc.mapper.OtcGcOrderMapper;
import com.qianxun.project.otc.domain.OtcGcOrder;

public interface IOtcGcOrderService{

    public int deleteByPrimaryKey(Integer id);

    
    public int insert(OtcGcOrder record);

    
    public int insertOrUpdate(OtcGcOrder record);

    
    public int insertOrUpdateSelective(OtcGcOrder record);

    
    public int insertSelective(OtcGcOrder record);

    
    public OtcGcOrder selectByPrimaryKey(Integer id);


    public List<OtcGcOrder> selectOtcGcOrderList(OtcGcOrder otcGcOrder);

    
    public int updateByPrimaryKeySelective(OtcGcOrder record);

    
    public int updateByPrimaryKey(OtcGcOrder record);

    
    public int updateBatch(List<OtcGcOrder> list);

    
    public int batchInsert(List<OtcGcOrder> list);

    /**
     * 我要卖GC
     * @return 返回订单号
     */
    String sellGc(OtcGcOrder otcGcOrder);


}
