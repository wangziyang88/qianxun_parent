package com.qianxun.project.coin.service.impl;

import java.util.List;
import com.qianxun.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qianxun.project.coin.mapper.CoinTypeMapper;
import com.qianxun.project.coin.domain.CoinType;
import com.qianxun.project.coin.service.ICoinTypeService;

@Service
public class CoinTypeServiceImpl implements ICoinTypeService 
{
    @Autowired
    private CoinTypeMapper coinTypeMapper;

    @Override
    public CoinType selectCoinTypeById(Long id)
    {
        return coinTypeMapper.selectCoinTypeById(id);
    }

    @Override
    public List<CoinType> selectCoinTypeList(CoinType coinType)
    {
        return coinTypeMapper.selectCoinTypeList(coinType);
    }

    @Override
    public int insertCoinType(CoinType coinType)
    {
        coinType.setCreateTime(DateUtils.getNowDate());
        return coinTypeMapper.insertCoinType(coinType);
    }

    @Override
    public int updateCoinType(CoinType coinType)
    {
        coinType.setUpdateTime(DateUtils.getNowDate());
        return coinTypeMapper.updateCoinType(coinType);
    }

    @Override
    public int deleteCoinTypeByIds(Long[] ids)
    {
        return coinTypeMapper.deleteCoinTypeByIds(ids);
    }

    @Override
    public int deleteCoinTypeById(Long id)
    {
        return coinTypeMapper.deleteCoinTypeById(id);
    }
}
