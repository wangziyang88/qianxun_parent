package com.qianxun.project.otc.domain;

import com.qianxun.framework.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

public class OtcGcOrder extends BaseEntity {
    /**
     * id
     */
    private Integer id;

    /**
     * otc GC订单号
     */
    private String orderNo;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 订单类型1:买 2：卖
     */
    private String orderType;

    /**
     * GC数量
     */
    private BigDecimal amount;

    /**
     * GC与RMB比例
     */
    private BigDecimal gcToRmb;

    /**
     * rmb总额
     */
    private BigDecimal totalRmb;

    /**
     * GC与USDT比例
     */
    private BigDecimal gcToUsdt;

    /**
     * USDT总额
     */
    private BigDecimal totalUsdt;

    /**
     * GC奖励比例
     */
    private BigDecimal rewardRatio;

    /**
     * 交易凭证
     */
    private String voucherPicture;

    /**
     * 订单状态（0：已取消 1：为上传凭证 2：已上传凭证 3：订单完成）
     */
    private String status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getGcToRmb() {
        return gcToRmb;
    }

    public void setGcToRmb(BigDecimal gcToRmb) {
        this.gcToRmb = gcToRmb;
    }

    public BigDecimal getTotalRmb() {
        return totalRmb;
    }

    public void setTotalRmb(BigDecimal totalRmb) {
        this.totalRmb = totalRmb;
    }

    public BigDecimal getGcToUsdt() {
        return gcToUsdt;
    }

    public void setGcToUsdt(BigDecimal gcToUsdt) {
        this.gcToUsdt = gcToUsdt;
    }

    public BigDecimal getTotalUsdt() {
        return totalUsdt;
    }

    public void setTotalUsdt(BigDecimal totalUsdt) {
        this.totalUsdt = totalUsdt;
    }

    public BigDecimal getRewardRatio() {
        return rewardRatio;
    }

    public void setRewardRatio(BigDecimal rewardRatio) {
        this.rewardRatio = rewardRatio;
    }

    public String getVoucherPicture() {
        return voucherPicture;
    }

    public void setVoucherPicture(String voucherPicture) {
        this.voucherPicture = voucherPicture;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "OtcGcOrder{" +
                "id=" + id +
                ", orderNo='" + orderNo + '\'' +
                ", userId=" + userId +
                ", orderType='" + orderType + '\'' +
                ", amount=" + amount +
                ", gcToRmb=" + gcToRmb +
                ", totalRmb=" + totalRmb +
                ", gcToUsdt=" + gcToUsdt +
                ", totalUsdt=" + totalUsdt +
                ", rewardRatio=" + rewardRatio +
                ", voucherPicture='" + voucherPicture + '\'' +
                ", status='" + status + '\'' +
                "} " + super.toString();
    }
}