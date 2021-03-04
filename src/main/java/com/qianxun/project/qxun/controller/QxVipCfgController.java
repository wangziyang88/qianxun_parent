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
import com.qianxun.project.qxun.domain.QxVipCfg;
import com.qianxun.project.qxun.service.IQxVipCfgService;
import com.qianxun.framework.web.controller.BaseController;
import com.qianxun.framework.web.domain.AjaxResult;
import com.qianxun.common.utils.ExcelUtil;
import com.qianxun.framework.web.page.TableDataInfo;

@RestController
@RequestMapping("/qx/vipCfg")
public class QxVipCfgController extends BaseController
{
    @Autowired
    private IQxVipCfgService qxVipCfgService;

    @PreAuthorize("@ss.hasPermi('qx:cfg:list')")
    @GetMapping("/list")
    public TableDataInfo list(QxVipCfg qxVipCfg)
    {
        startPage();
        List<QxVipCfg> list = qxVipCfgService.selectQxVipCfgList(qxVipCfg);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('qx:cfg:export')")
    @Log(title = "VIP奖配置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(QxVipCfg qxVipCfg)
    {
        List<QxVipCfg> list = qxVipCfgService.selectQxVipCfgList(qxVipCfg);
        ExcelUtil<QxVipCfg> util = new ExcelUtil<QxVipCfg>(QxVipCfg.class);
        return util.exportExcel(list, "cfg");
    }

    @PreAuthorize("@ss.hasPermi('qx:cfg:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(qxVipCfgService.selectQxVipCfgById(id));
    }

    @PreAuthorize("@ss.hasPermi('qx:cfg:add')")
    @Log(title = "VIP奖配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QxVipCfg qxVipCfg)
    {
        return toAjax(qxVipCfgService.insertQxVipCfg(qxVipCfg));
    }

    @PreAuthorize("@ss.hasPermi('qx:cfg:edit')")
    @Log(title = "VIP奖配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QxVipCfg qxVipCfg)
    {
        return toAjax(qxVipCfgService.updateQxVipCfg(qxVipCfg));
    }

    @PreAuthorize("@ss.hasPermi('qx:cfg:remove')")
    @Log(title = "VIP奖配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(qxVipCfgService.deleteQxVipCfgByIds(ids));
    }
}
