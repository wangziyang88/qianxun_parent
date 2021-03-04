package com.qianxun.project.qxun.service;

import com.qianxun.project.qxun.domain.QxGiftRec;
import com.qianxun.project.qxun.domain.vo.BuyVo;

import java.util.List;

public interface IQxGiftRecService {
    public QxGiftRec selectQxGiftRecById(Long id);

    public List<QxGiftRec> selectQxGiftRecList(QxGiftRec qxGiftRec);

    public int insertQxGiftRec(QxGiftRec qxGiftRec);

    public int updateQxGiftRec(QxGiftRec qxGiftRec);

    public int deleteQxGiftRecByIds(Long[] ids);

    public int deleteQxGiftRecById(Long id);

    public void buy(long userId, BuyVo buyVo);
}
