package com.qianxun.project.qxun.mapper;

import java.util.List;
import com.qianxun.project.qxun.domain.QxVipCfg;

public interface QxVipCfgMapper 
{
    public QxVipCfg selectQxVipCfgById(Long id);

    public List<QxVipCfg> selectQxVipCfgList(QxVipCfg qxVipCfg);

    public int insertQxVipCfg(QxVipCfg qxVipCfg);

    public int updateQxVipCfg(QxVipCfg qxVipCfg);

    public int deleteQxVipCfgById(Long id);

    public int deleteQxVipCfgByIds(Long[] ids);
}
