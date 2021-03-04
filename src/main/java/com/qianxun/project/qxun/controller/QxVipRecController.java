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
import com.qianxun.project.qxun.domain.QxVipRec;
import com.qianxun.project.qxun.service.IQxVipRecService;
import com.qianxun.framework.web.controller.BaseController;
import com.qianxun.framework.web.domain.AjaxResult;
import com.qianxun.common.utils.ExcelUtil;
import com.qianxun.framework.web.page.TableDataInfo;

@RestController
@RequestMapping("/qx/vipRec")
public class QxVipRecController extends BaseController
{
    @Autowired
    private IQxVipRecService qxVipRecService;

    @PreAuthorize("@ss.hasPermi('qx:rec:list')")
    @GetMapping("/list")
    public TableDataInfo list(QxVipRec qxVipRec)
    {
        startPage();
        List<QxVipRec> list = qxVipRecService.selectQxVipRecList(qxVipRec);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('qx:rec:export')")
    @Log(title = "VIP奖记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(QxVipRec qxVipRec)
    {
        List<QxVipRec> list = qxVipRecService.selectQxVipRecList(qxVipRec);
        ExcelUtil<QxVipRec> util = new ExcelUtil<QxVipRec>(QxVipRec.class);
        return util.exportExcel(list, "rec");
    }

    @PreAuthorize("@ss.hasPermi('qx:rec:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(qxVipRecService.selectQxVipRecById(id));
    }

    @PreAuthorize("@ss.hasPermi('qx:rec:add')")
    @Log(title = "VIP奖记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QxVipRec qxVipRec)
    {
        return toAjax(qxVipRecService.insertQxVipRec(qxVipRec));
    }

    @PreAuthorize("@ss.hasPermi('qx:rec:edit')")
    @Log(title = "VIP奖记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QxVipRec qxVipRec)
    {
        return toAjax(qxVipRecService.updateQxVipRec(qxVipRec));
    }

    @PreAuthorize("@ss.hasPermi('qx:rec:remove')")
    @Log(title = "VIP奖记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(qxVipRecService.deleteQxVipRecByIds(ids));
    }
}
