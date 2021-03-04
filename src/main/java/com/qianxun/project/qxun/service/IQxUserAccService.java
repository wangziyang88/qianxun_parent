package com.qianxun.project.qxun.service;

import com.qianxun.project.qxun.domain.QxUserAcc;

import java.util.List;

public interface IQxUserAccService 
{
    public QxUserAcc selectQxUserAccById(Long id);

    public List<QxUserAcc> selectQxUserAccList(QxUserAcc qxUserAcc);

    public int insertQxUserAcc(QxUserAcc qxUserAcc);

    public int updateQxUserAcc(QxUserAcc qxUserAcc);

    public int deleteQxUserAccByIds(Long[] ids);

    public int deleteQxUserAccById(Long id);

    public QxUserAcc selectQxUserAccByUserId(long userId);
}
