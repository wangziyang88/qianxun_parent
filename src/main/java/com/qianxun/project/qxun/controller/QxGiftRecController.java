package com.qianxun.project.qxun.controller;

import java.util.List;

import com.qianxun.common.utils.ExcelUtil;
import com.qianxun.framework.aspectj.lang.annotation.Log;
import com.qianxun.framework.aspectj.lang.enums.BusinessType;
import com.qianxun.framework.web.controller.BaseController;
import com.qianxun.framework.web.domain.AjaxResult;
import com.qianxun.framework.web.page.TableDataInfo;
import com.qianxun.project.qxun.domain.QxGiftRec;
import com.qianxun.project.qxun.service.IQxGiftRecService;
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
@RequestMapping("/qx/giftRec")
public class QxGiftRecController extends BaseController
{
    @Autowired
    private IQxGiftRecService qxGiftRecService;

    @PreAuthorize("@ss.hasPermi('qx:rec:list')")
    @GetMapping("/list")
    public TableDataInfo list(QxGiftRec qxGiftRec)
    {
        startPage();
        List<QxGiftRec> list = qxGiftRecService.selectQxGiftRecList(qxGiftRec);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('qx:rec:export')")
    @Log(title = "礼包购买记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(QxGiftRec qxGiftRec)
    {
        List<QxGiftRec> list = qxGiftRecService.selectQxGiftRecList(qxGiftRec);
        ExcelUtil<QxGiftRec> util = new ExcelUtil<QxGiftRec>(QxGiftRec.class);
        return util.exportExcel(list, "rec");
    }

    @PreAuthorize("@ss.hasPermi('qx:rec:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(qxGiftRecService.selectQxGiftRecById(id));
    }

    @PreAuthorize("@ss.hasPermi('qx:rec:add')")
    @Log(title = "礼包购买记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QxGiftRec qxGiftRec)
    {
        return toAjax(qxGiftRecService.insertQxGiftRec(qxGiftRec));
    }

    @PreAuthorize("@ss.hasPermi('qx:rec:edit')")
    @Log(title = "礼包购买记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QxGiftRec qxGiftRec)
    {
        return toAjax(qxGiftRecService.updateQxGiftRec(qxGiftRec));
    }

    @PreAuthorize("@ss.hasPermi('qx:rec:remove')")
    @Log(title = "礼包购买记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(qxGiftRecService.deleteQxGiftRecByIds(ids));
    }
}
