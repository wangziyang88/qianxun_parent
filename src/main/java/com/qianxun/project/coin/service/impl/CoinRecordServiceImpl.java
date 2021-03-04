package com.qianxun.project.coin.service.impl;

import java.util.List;
import com.qianxun.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qianxun.project.coin.mapper.CoinRecordMapper;
import com.qianxun.project.coin.domain.CoinRecord;
import com.qianxun.project.coin.service.ICoinRecordService;

@Service
public class CoinRecordServiceImpl implements ICoinRecordService {
    @Autowired
    private CoinRecordMapper coinRecordMapper;

    @Override
    public CoinRecord selectCoinRecordById(Long id)
    {
        return coinRecordMapper.selectCoinRecordById(id);
    }

    @Override
    public List<CoinRecord> selectCoinRecordList(CoinRecord coinRecord)
    {
        return coinRecordMapper.selectCoinRecordList(coinRecord);
    }

    @Override
    public int insertCoinRecord(CoinRecord coinRecord)
    {
        coinRecord.setCreateTime(DateUtils.getNowDate());
        return coinRecordMapper.insertCoinRecord(coinRecord);
    }

    @Override
    public int updateCoinRecord(CoinRecord coinRecord)
    {
        coinRecord.setUpdateTime(DateUtils.getNowDate());
        return coinRecordMapper.updateCoinRecord(coinRecord);
    }

    @Override
    public int deleteCoinRecordByIds(Long[] ids)
    {
        return coinRecordMapper.deleteCoinRecordByIds(ids);
    }

    @Override
    public int deleteCoinRecordById(Long id)
    {
        return coinRecordMapper.deleteCoinRecordById(id);
    }

    @Override
    public CoinRecord selectCoinRecordByTxId(String txId) {
        return coinRecordMapper.selectCoinRecordByTxId(txId);
    }



}
