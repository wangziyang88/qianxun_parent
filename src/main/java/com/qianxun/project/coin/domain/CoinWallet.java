package com.qianxun.project.coin.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.qianxun.framework.aspectj.lang.annotation.Excel;
import com.qianxun.framework.web.domain.BaseEntity;

public class CoinWallet extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @Excel(name = "ID")
    private Long id;

    @Excel(name = "用户ID")
    private Long userId;

    @Excel(name = "币种ID")
    private Long coinId;

    @Excel(name = "币种名称")
    private String coinName;

    @Excel(name = "钱包类型", readConverterExp = "online=在线签名,offline=离线签名,thirdparty=第三方")
    private String walletType;

    @Excel(name = "主链类型", readConverterExp = "main=主链,omni=Omni协议,erc20=以太坊代币,trc20=波场代币")
    private String mainCoinType;

    @Excel(name = "钱包地址")
    private String address;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setCoinId(Long coinId) 
    {
        this.coinId = coinId;
    }

    public Long getCoinId() 
    {
        return coinId;
    }
    public void setCoinName(String coinName) 
    {
        this.coinName = coinName;
    }

    public String getCoinName() 
    {
        return coinName;
    }
    public void setWalletType(String walletType) 
    {
        this.walletType = walletType;
    }

    public String getWalletType() 
    {
        return walletType;
    }
    public void setMainCoinType(String mainCoinType) 
    {
        this.mainCoinType = mainCoinType;
    }

    public String getMainCoinType() 
    {
        return mainCoinType;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("coinId", getCoinId())
            .append("coinName", getCoinName())
            .append("walletType", getWalletType())
            .append("mainCoinType", getMainCoinType())
            .append("address", getAddress())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
