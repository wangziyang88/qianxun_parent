package com.qianxun.project.qxun.service;

import com.qianxun.project.qxun.domain.QxGiftCfg;

import java.util.List;

public interface IQxGiftCfgService 
{
    public QxGiftCfg selectQxGiftCfgById(Long id);

    public List<QxGiftCfg> selectQxGiftCfgList(QxGiftCfg qxGiftCfg);

    public int insertQxGiftCfg(QxGiftCfg qxGiftCfg);

    public int updateQxGiftCfg(QxGiftCfg qxGiftCfg);

    public int deleteQxGiftCfgByIds(Long[] ids);

    public int deleteQxGiftCfgById(Long id);
}
