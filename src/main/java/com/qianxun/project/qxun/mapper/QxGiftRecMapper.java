package com.qianxun.project.qxun.mapper;

import java.math.BigDecimal;
import java.util.List;
import com.qianxun.project.qxun.domain.QxGiftRec;

public interface QxGiftRecMapper 
{
    public QxGiftRec selectQxGiftRecById(Long id);

    public List<QxGiftRec> selectQxGiftRecList(QxGiftRec qxGiftRec);

    public int insertQxGiftRec(QxGiftRec qxGiftRec);

    public int updateQxGiftRec(QxGiftRec qxGiftRec);

    public int deleteQxGiftRecById(Long id);

    public int deleteQxGiftRecByIds(Long[] ids);

    public BigDecimal selectQxGiftRecByUserIdGcTotal(long userId);
}
