package com.qianxun.project.coin.mapper;

import java.util.List;
import com.qianxun.project.coin.domain.CoinRecord;

public interface CoinRecordMapper 
{
    public CoinRecord selectCoinRecordById(Long id);

    public List<CoinRecord> selectCoinRecordList(CoinRecord coinRecord);

    public int insertCoinRecord(CoinRecord coinRecord);

    public int updateCoinRecord(CoinRecord coinRecord);

    public int deleteCoinRecordById(Long id);

    public int deleteCoinRecordByIds(Long[] ids);

    public CoinRecord selectCoinRecordByTxId(String txId);
}
