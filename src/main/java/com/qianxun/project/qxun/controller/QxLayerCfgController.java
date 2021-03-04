package com.qianxun.project.qxun.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.qianxun.framework.aspectj.lang.annotation.Log;
import com.qianxun.framework.aspectj.lang.enums.BusinessType;
import com.qianxun.project.qxun.domain.QxLayerCfg;
import com.qianxun.project.qxun.service.IQxLayerCfgService;
import com.qianxun.framework.web.controller.BaseController;
import com.qianxun.framework.web.domain.AjaxResult;
import com.qianxun.common.utils.ExcelUtil;
import com.qianxun.framework.web.page.TableDataInfo;

@RestController
@RequestMapping("/qx/layerCfg")
public class QxLayerCfgController extends BaseController
{
    @Autowired
    private IQxLayerCfgService qxLayerCfgService;

    @PreAuthorize("@ss.hasPermi('qx:cfg:list')")
    @GetMapping("/list")
    public TableDataInfo list(QxLayerCfg qxLayerCfg)
    {
        startPage();
        List<QxLayerCfg> list = qxLayerCfgService.selectQxLayerCfgList(qxLayerCfg);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('qx:cfg:export')")
    @Log(title = "层奖配置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(QxLayerCfg qxLayerCfg)
    {
        List<QxLayerCfg> list = qxLayerCfgService.selectQxLayerCfgList(qxLayerCfg);
        ExcelUtil<QxLayerCfg> util = new ExcelUtil<QxLayerCfg>(QxLayerCfg.class);
        return util.exportExcel(list, "cfg");
    }

    @PreAuthorize("@ss.hasPermi('qx:cfg:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(qxLayerCfgService.selectQxLayerCfgById(id));
    }

    @PreAuthorize("@ss.hasPermi('qx:cfg:add')")
    @Log(title = "层奖配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QxLayerCfg qxLayerCfg)
    {
        return toAjax(qxLayerCfgService.insertQxLayerCfg(qxLayerCfg));
    }

    @PreAuthorize("@ss.hasPermi('qx:cfg:edit')")
    @Log(title = "层奖配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QxLayerCfg qxLayerCfg)
    {
        return toAjax(qxLayerCfgService.updateQxLayerCfg(qxLayerCfg));
    }

    @PreAuthorize("@ss.hasPermi('qx:cfg:remove')")
    @Log(title = "层奖配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(qxLayerCfgService.deleteQxLayerCfgByIds(ids));
    }
}
