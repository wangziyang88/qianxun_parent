package com.qianxun.project.coin.service;

import java.util.List;
import com.qianxun.project.coin.domain.CoinType;

public interface ICoinTypeService 
{
    public CoinType selectCoinTypeById(Long id);

    public List<CoinType> selectCoinTypeList(CoinType coinType);

    public int insertCoinType(CoinType coinType);

    public int updateCoinType(CoinType coinType);

    public int deleteCoinTypeByIds(Long[] ids);

    public int deleteCoinTypeById(Long id);
}
