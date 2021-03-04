package com.qianxun.project.coin.domain.vo;

import java.math.BigDecimal;

import com.qianxun.common.enums.CoinConstants.IncomeType;
import com.qianxun.common.enums.CoinConstants.MainType;

/**
 * 操作资产VO类
 *
 */
public class BalanceChange {
	
	/**用户ID*/
	private Long userId;
	
	/**币种ID*/
    private Long coinId;
	
	/**币种名称(大写)*/
	private String coinName;
	
	/**操作余额(正数加余额/负数减余额)**/
	private BigDecimal available;
	
	/**操作冻结(正数加冻结/负数减冻结)**/
	private BigDecimal frozen;
	
	/**操作锁仓(正数加锁仓/负数减锁仓)**/
    private BigDecimal lockup;
    
    /**收支类型**/
    private IncomeType incomeType;
	
	/**操作主类型**/
	private MainType mainType;

	/**操作子类型**/
    private String sonType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public BigDecimal getAvailable() {
        return available;
    }

    public void setAvailable(BigDecimal available) {
        this.available = available;
    }

    public BigDecimal getFrozen() {
        return frozen;
    }

    public void setFrozen(BigDecimal frozen) {
        this.frozen = frozen;
    }

    public BigDecimal getLockup() {
        return lockup;
    }

    public void setLockup(BigDecimal lockup) {
        this.lockup = lockup;
    }
    
    public IncomeType getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(IncomeType incomeType) {
        this.incomeType = incomeType;
    }

    public MainType getMainType() {
        return mainType;
    }

    public void setMainType(MainType mainType) {
        this.mainType = mainType;
    }

    public String getSonType() {
        return sonType;
    }

    public void setSonType(String sonType) {
        this.sonType = sonType;
    }
    
}