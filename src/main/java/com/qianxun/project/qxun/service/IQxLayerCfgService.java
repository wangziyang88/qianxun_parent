package com.qianxun.project.qxun.service;

import java.util.List;
import com.qianxun.project.qxun.domain.QxLayerCfg;

public interface IQxLayerCfgService 
{
    public QxLayerCfg selectQxLayerCfgById(Long id);

    public List<QxLayerCfg> selectQxLayerCfgList(QxLayerCfg qxLayerCfg);

    public int insertQxLayerCfg(QxLayerCfg qxLayerCfg);

    public int updateQxLayerCfg(QxLayerCfg qxLayerCfg);

    public int deleteQxLayerCfgByIds(Long[] ids);

    public int deleteQxLayerCfgById(Long id);
}
