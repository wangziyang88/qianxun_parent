package com.qianxun.project.qxun.mapper;

import com.qianxun.project.qxun.domain.QxUserAcc;

import java.util.List;

public interface QxUserAccMapper 
{
    public QxUserAcc selectQxUserAccById(Long id);

    public List<QxUserAcc> selectQxUserAccList(QxUserAcc qxUserAcc);

    public int insertQxUserAcc(QxUserAcc qxUserAcc);

    public int updateQxUserAcc(QxUserAcc qxUserAcc);

    public int deleteQxUserAccById(Long id);

    public int deleteQxUserAccByIds(Long[] ids);

    public QxUserAcc selectQxUserAccByUserId(long userId);
}
