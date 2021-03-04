package com.qianxun.project.coin.service;

import java.util.List;
import com.qianxun.project.coin.domain.CoinRecord;

public interface ICoinRecordService 
{
    public CoinRecord selectCoinRecordById(Long id);

    public List<CoinRecord> selectCoinRecordList(CoinRecord coinRecord);

    public int insertCoinRecord(CoinRecord coinRecord);

    public int updateCoinRecord(CoinRecord coinRecord);

    public int deleteCoinRecordByIds(Long[] ids);

    public int deleteCoinRecordById(Long id);

    public CoinRecord selectCoinRecordByTxId(String txId);
}
