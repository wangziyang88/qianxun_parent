package com.qianxun.project.qxun.controller;

import java.util.List;

import com.qianxun.common.utils.ExcelUtil;
import com.qianxun.framework.aspectj.lang.annotation.Log;
import com.qianxun.framework.aspectj.lang.enums.BusinessType;
import com.qianxun.framework.web.controller.BaseController;
import com.qianxun.framework.web.domain.AjaxResult;
import com.qianxun.framework.web.page.TableDataInfo;
import com.qianxun.project.qxun.domain.QxUserAcc;
import com.qianxun.project.qxun.service.IQxUserAccService;
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
@RequestMapping("/qx/userAcc")
public class QxUserAccController extends BaseController
{
    @Autowired
    private IQxUserAccService qxUserAccService;

    @PreAuthorize("@ss.hasPermi('qx:acc:list')")
    @GetMapping("/list")
    public TableDataInfo list(QxUserAcc qxUserAcc)
    {
        startPage();
        List<QxUserAcc> list = qxUserAccService.selectQxUserAccList(qxUserAcc);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('qx:acc:export')")
    @Log(title = "用户账户", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(QxUserAcc qxUserAcc)
    {
        List<QxUserAcc> list = qxUserAccService.selectQxUserAccList(qxUserAcc);
        ExcelUtil<QxUserAcc> util = new ExcelUtil<QxUserAcc>(QxUserAcc.class);
        return util.exportExcel(list, "acc");
    }

    @PreAuthorize("@ss.hasPermi('qx:acc:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(qxUserAccService.selectQxUserAccById(id));
    }

    @PreAuthorize("@ss.hasPermi('qx:acc:add')")
    @Log(title = "用户账户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QxUserAcc qxUserAcc)
    {
        return toAjax(qxUserAccService.insertQxUserAcc(qxUserAcc));
    }

    @PreAuthorize("@ss.hasPermi('qx:acc:edit')")
    @Log(title = "用户账户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QxUserAcc qxUserAcc)
    {
        return toAjax(qxUserAccService.updateQxUserAcc(qxUserAcc));
    }

    @PreAuthorize("@ss.hasPermi('qx:acc:remove')")
    @Log(title = "用户账户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(qxUserAccService.deleteQxUserAccByIds(ids));
    }
}
