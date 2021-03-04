package com.qianxun.project.otc.controller.app;

import com.qianxun.common.constant.Constants;
import com.qianxun.common.enums.OrderStatus;
import com.qianxun.common.enums.OrderType;
import com.qianxun.common.utils.DateUtils;
import com.qianxun.common.utils.StringUtils;
import com.qianxun.framework.aspectj.lang.annotation.Log;
import com.qianxun.framework.aspectj.lang.enums.BusinessType;
import com.qianxun.framework.redis.RedisCache;
import com.qianxun.framework.web.controller.BaseController;
import com.qianxun.framework.web.domain.AjaxResult;
import com.qianxun.project.otc.domain.OtcGcOrder;
import com.qianxun.project.otc.service.IOtcGcOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:
 * @Email:
 * @Description:
 * @Date:2021/3/1 14:00
 * @Version:1.0
 */
@RestController
@RequestMapping("/qianxun/otc")
public class OtcController extends BaseController {

    @Autowired
    private IOtcGcOrderService otcGcOrderService;

    @Autowired
    private RedisCache redisCache;


    /**
     * 上架GC
     */
    @PostMapping("sellGc")
    @Log(title = "GC订单", businessType = BusinessType.INSERT)
    public AjaxResult sellGc(@RequestBody @Validated OtcGcOrder otcGcOrder){

        String orderNum=otcGcOrderService.sellGc(otcGcOrder);

        return AjaxResult.success();
    }

    /**
     * 选择要购买GC订单
     */
    @PutMapping("chooseOrder/{orderNum}")
    public AjaxResult chooseOrder(@PathVariable String orderNum){

        //查看当前用户是否有未完成的订单
        OtcGcOrder otcGcOrder=new OtcGcOrder();
        otcGcOrder.setUserId(getUserId());
        otcGcOrder.setOrderType(OrderType.BUY.getCode());
        otcGcOrder.setStatus(OrderStatus.NOHAVEVOUCHER.getCode());
        List<OtcGcOrder> orders = otcGcOrderService.selectOtcGcOrderList(otcGcOrder);
        if (orders.size()>0){

        }
        otcGcOrder = orders.get(0);
        System.out.println(otcGcOrder);

        otcGcOrder.setOrderType(OrderType.BUY.getCode());
        otcGcOrder.setCreateTime(DateUtils.getNowDate());
        otcGcOrder.setStatus(OrderStatus.NOHAVEVOUCHER.getCode());
        otcGcOrder.setUserId(getUserId());


        //OtcGcOrder otcGcOrder = redisCache.getCacheObject(Constants.OTC_GC_ORDER_KEY + getUserId());
        if (StringUtils.isNotNull(otcGcOrder)){
            return AjaxResult.success(otcGcOrder);
        }
       // otcGcOrder=otcGcOrderService.selectByPrimaryKey(orderNum);


        redisCache.setCacheObject(Constants.OTC_GC_ORDER_KEY + getUserId(),otcGcOrder);
        //根据用户id生成redis缓存 存储订单信息
        return AjaxResult.success(otcGcOrder);
    }




    /**
     * 购买GC
     */
    @PostMapping("buyGc")
    @Log(title = "GC订单", businessType = BusinessType.INSERT)
    public AjaxResult buyGc(@RequestBody @Validated OtcGcOrder otcGcOrder){
        //将这个订单锁定

        //将订单详情存入redis

        //

        String orderNum=otcGcOrderService.sellGc(otcGcOrder);



        return AjaxResult.success();
    }




}
