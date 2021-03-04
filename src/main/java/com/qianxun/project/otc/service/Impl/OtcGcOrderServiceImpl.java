package com.qianxun.project.otc.service.Impl;

import com.qianxun.common.enums.OrderStatus;
import com.qianxun.common.enums.OrderType;
import com.qianxun.common.utils.DateUtils;
import com.qianxun.common.utils.OrderNoUtil;
import com.qianxun.project.otc.domain.OtcGcOrder;
import com.qianxun.project.otc.mapper.OtcGcOrderMapper;
import com.qianxun.project.otc.service.IOtcGcOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OtcGcOrderServiceImpl implements IOtcGcOrderService {

    @Autowired
    private OtcGcOrderMapper otcGcOrderMapper;


    @Override
    public int deleteByPrimaryKey(Integer id) {
        return otcGcOrderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(OtcGcOrder record) {
        return otcGcOrderMapper.insert(record);
    }

    @Override
    public int insertOrUpdate(OtcGcOrder record) {
        return otcGcOrderMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OtcGcOrder record) {
        return otcGcOrderMapper.insertOrUpdateSelective(record);
    }

    @Override
    public int insertSelective(OtcGcOrder record) {
        return otcGcOrderMapper.insertSelective(record);
    }

    @Override
    public OtcGcOrder selectByPrimaryKey(Integer id) {
        return otcGcOrderMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<OtcGcOrder> selectOtcGcOrderList(OtcGcOrder otcGcOrder) {
        return otcGcOrderMapper.selectOtcGcOrderList(otcGcOrder);
    }

    @Override
    public int updateByPrimaryKeySelective(OtcGcOrder record) {
        return otcGcOrderMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OtcGcOrder record) {
        return otcGcOrderMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateBatch(List<OtcGcOrder> list) {
        return otcGcOrderMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OtcGcOrder> list) {
        return otcGcOrderMapper.batchInsert(list);
    }

    /**
     * 我要卖GC
     *
     * @param otcGcOrder
     * @return 返回订单号
     */
    @Override
    public String sellGc(OtcGcOrder otcGcOrder) {


        //gc数量
        BigDecimal amount = otcGcOrder.getAmount();

        //

        //未上传凭证
        otcGcOrder.setStatus(OrderStatus.NOHAVEVOUCHER.getCode());

        //订单状态卖
        otcGcOrder.setOrderType(OrderType.SELL.getCode());

        //订单生成时间
        otcGcOrder.setCreateTime(DateUtils.getNowDate());

        //订单号
        otcGcOrder.setOrderNo(OrderNoUtil.getOrderNo());

        //将订单存入库中
        otcGcOrderMapper.insert(otcGcOrder);

        return otcGcOrder.getOrderNo();
    }



}
