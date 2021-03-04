package com.qianxun.project.qxun.mapper;

import java.util.List;
import com.qianxun.project.qxun.domain.QxLayerCfg;

public interface QxLayerCfgMapper 
{
    public QxLayerCfg selectQxLayerCfgById(Long id);

    public List<QxLayerCfg> selectQxLayerCfgList(QxLayerCfg qxLayerCfg);

    public int insertQxLayerCfg(QxLayerCfg qxLayerCfg);

    public int updateQxLayerCfg(QxLayerCfg qxLayerCfg);

    public int deleteQxLayerCfgById(Long id);

    public int deleteQxLayerCfgByIds(Long[] ids);
}
