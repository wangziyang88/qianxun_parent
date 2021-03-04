package com.qianxun.project.qxun.mapper;

import com.qianxun.project.qxun.domain.QxGiftCfg;

import java.util.List;

public interface QxGiftCfgMapper 
{
    public QxGiftCfg selectQxGiftCfgById(Long id);

    public List<QxGiftCfg> selectQxGiftCfgList(QxGiftCfg qxGiftCfg);

    public int insertQxGiftCfg(QxGiftCfg qxGiftCfg);

    public int updateQxGiftCfg(QxGiftCfg qxGiftCfg);

    public int deleteQxGiftCfgById(Long id);

    public int deleteQxGiftCfgByIds(Long[] ids);
}
