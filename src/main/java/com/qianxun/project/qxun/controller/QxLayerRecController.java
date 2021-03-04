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
import com.qianxun.project.qxun.domain.QxLayerRec;
import com.qianxun.project.qxun.service.IQxLayerRecService;
import com.qianxun.framework.web.controller.BaseController;
import com.qianxun.framework.web.domain.AjaxResult;
import com.qianxun.common.utils.ExcelUtil;
import com.qianxun.framework.web.page.TableDataInfo;

@RestController
@RequestMapping("/qx/layerRec")
public class QxLayerRecController extends BaseController
{
    @Autowired
    private IQxLayerRecService qxLayerRecService;

    @PreAuthorize("@ss.hasPermi('qx:rec:list')")
    @GetMapping("/list")
    public TableDataInfo list(QxLayerRec qxLayerRec)
    {
        startPage();
        List<QxLayerRec> list = qxLayerRecService.selectQxLayerRecList(qxLayerRec);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('qx:rec:export')")
    @Log(title = "层奖记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(QxLayerRec qxLayerRec)
    {
        List<QxLayerRec> list = qxLayerRecService.selectQxLayerRecList(qxLayerRec);
        ExcelUtil<QxLayerRec> util = new ExcelUtil<QxLayerRec>(QxLayerRec.class);
        return util.exportExcel(list, "rec");
    }

    @PreAuthorize("@ss.hasPermi('qx:rec:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(qxLayerRecService.selectQxLayerRecById(id));
    }

    @PreAuthorize("@ss.hasPermi('qx:rec:add')")
    @Log(title = "层奖记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QxLayerRec qxLayerRec)
    {
        return toAjax(qxLayerRecService.insertQxLayerRec(qxLayerRec));
    }

    @PreAuthorize("@ss.hasPermi('qx:rec:edit')")
    @Log(title = "层奖记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QxLayerRec qxLayerRec)
    {
        return toAjax(qxLayerRecService.updateQxLayerRec(qxLayerRec));
    }

    @PreAuthorize("@ss.hasPermi('qx:rec:remove')")
    @Log(title = "层奖记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(qxLayerRecService.deleteQxLayerRecByIds(ids));
    }
}
