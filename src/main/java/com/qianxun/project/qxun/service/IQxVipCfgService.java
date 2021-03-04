package com.qianxun.project.qxun.service;

import com.qianxun.project.qxun.domain.QxVipCfg;

import java.util.List;

public interface IQxVipCfgService 
{
    public QxVipCfg selectQxVipCfgById(Long id);

    public List<QxVipCfg> selectQxVipCfgList(QxVipCfg qxVipCfg);

    public int insertQxVipCfg(QxVipCfg qxVipCfg);

    public int updateQxVipCfg(QxVipCfg qxVipCfg);

    public int deleteQxVipCfgByIds(Long[] ids);

    public int deleteQxVipCfgById(Long id);
}
