package com.qianxun.project.coin.mapper;

import java.util.List;
import com.qianxun.project.coin.domain.CoinType;

public interface CoinTypeMapper 
{
    public CoinType selectCoinTypeById(Long id);

    public List<CoinType> selectCoinTypeList(CoinType coinType);

    public int insertCoinType(CoinType coinType);

    public int updateCoinType(CoinType coinType);

    public int deleteCoinTypeById(Long id);

    public int deleteCoinTypeByIds(Long[] ids);
}
