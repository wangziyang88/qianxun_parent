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
import com.qianxun.project.qxun.domain.QxShareRec;
import com.qianxun.project.qxun.service.IQxShareRecService;
import com.qianxun.framework.web.controller.BaseController;
import com.qianxun.framework.web.domain.AjaxResult;
import com.qianxun.common.utils.ExcelUtil;
import com.qianxun.framework.web.page.TableDataInfo;

@RestController
@RequestMapping("/qx/shareRec")
public class QxShareRecController extends BaseController
{
    @Autowired
    private IQxShareRecService qxShareRecService;

    @PreAuthorize("@ss.hasPermi('qx:rec:list')")
    @GetMapping("/list")
    public TableDataInfo list(QxShareRec qxShareRec)
    {
        startPage();
        List<QxShareRec> list = qxShareRecService.selectQxShareRecList(qxShareRec);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('qx:rec:export')")
    @Log(title = "分享奖记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(QxShareRec qxShareRec)
    {
        List<QxShareRec> list = qxShareRecService.selectQxShareRecList(qxShareRec);
        ExcelUtil<QxShareRec> util = new ExcelUtil<QxShareRec>(QxShareRec.class);
        return util.exportExcel(list, "rec");
    }

    @PreAuthorize("@ss.hasPermi('qx:rec:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(qxShareRecService.selectQxShareRecById(id));
    }

    @PreAuthorize("@ss.hasPermi('qx:rec:add')")
    @Log(title = "分享奖记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QxShareRec qxShareRec)
    {
        return toAjax(qxShareRecService.insertQxShareRec(qxShareRec));
    }

    @PreAuthorize("@ss.hasPermi('qx:rec:edit')")
    @Log(title = "分享奖记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QxShareRec qxShareRec)
    {
        return toAjax(qxShareRecService.updateQxShareRec(qxShareRec));
    }

    @PreAuthorize("@ss.hasPermi('qx:rec:remove')")
    @Log(title = "分享奖记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(qxShareRecService.deleteQxShareRecByIds(ids));
    }
}
