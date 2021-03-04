package com.qianxun.project.qxun.controller;

import java.util.List;

import com.qianxun.common.utils.ExcelUtil;
import com.qianxun.framework.aspectj.lang.annotation.Log;
import com.qianxun.framework.aspectj.lang.enums.BusinessType;
import com.qianxun.framework.web.controller.BaseController;
import com.qianxun.framework.web.domain.AjaxResult;
import com.qianxun.framework.web.page.TableDataInfo;
import com.qianxun.project.qxun.domain.QxGiftCfg;
import com.qianxun.project.qxun.service.IQxGiftCfgService;
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


@RestController
@RequestMapping("/qx/qiftCfg")
public class QxGiftCfgController extends BaseController
{
    @Autowired
    private IQxGiftCfgService qxGiftCfgService;

    @PreAuthorize("@ss.hasPermi('qx:cfg:list')")
    @GetMapping("/list")
    public TableDataInfo list(QxGiftCfg qxGiftCfg)
    {
        startPage();
        List<QxGiftCfg> list = qxGiftCfgService.selectQxGiftCfgList(qxGiftCfg);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('qx:cfg:export')")
    @Log(title = "礼包配置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(QxGiftCfg qxGiftCfg)
    {
        List<QxGiftCfg> list = qxGiftCfgService.selectQxGiftCfgList(qxGiftCfg);
        ExcelUtil<QxGiftCfg> util = new ExcelUtil<QxGiftCfg>(QxGiftCfg.class);
        return util.exportExcel(list, "cfg");
    }

    @PreAuthorize("@ss.hasPermi('qx:cfg:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(qxGiftCfgService.selectQxGiftCfgById(id));
    }

    @PreAuthorize("@ss.hasPermi('qx:cfg:add')")
    @Log(title = "礼包配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QxGiftCfg qxGiftCfg)
    {
        return toAjax(qxGiftCfgService.insertQxGiftCfg(qxGiftCfg));
    }

    @PreAuthorize("@ss.hasPermi('qx:cfg:edit')")
    @Log(title = "礼包配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QxGiftCfg qxGiftCfg)
    {
        return toAjax(qxGiftCfgService.updateQxGiftCfg(qxGiftCfg));
    }

    @PreAuthorize("@ss.hasPermi('qx:cfg:remove')")
    @Log(title = "礼包配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(qxGiftCfgService.deleteQxGiftCfgByIds(ids));
    }
}
