package com.qianxun.project.coin.controller;

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
import com.qianxun.project.coin.domain.CoinType;
import com.qianxun.project.coin.service.ICoinTypeService;
import com.qianxun.framework.web.controller.BaseController;
import com.qianxun.framework.web.domain.AjaxResult;
import com.qianxun.common.utils.ExcelUtil;
import com.qianxun.framework.web.page.TableDataInfo;

@RestController
@RequestMapping("/coin/cointype")
public class CoinTypeController extends BaseController
{
    @Autowired
    private ICoinTypeService coinTypeService;

    @PreAuthorize("@ss.hasPermi('coin:cointype:list')")
    @GetMapping("/list")
    public TableDataInfo list(CoinType coinType)
    {
        startPage();
        List<CoinType> list = coinTypeService.selectCoinTypeList(coinType);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('coin:cointype:export')")
    @Log(title = "币种类型", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CoinType coinType)
    {
        List<CoinType> list = coinTypeService.selectCoinTypeList(coinType);
        ExcelUtil<CoinType> util = new ExcelUtil<CoinType>(CoinType.class);
        return util.exportExcel(list, "cointype");
    }

    @PreAuthorize("@ss.hasPermi('coin:cointype:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(coinTypeService.selectCoinTypeById(id));
    }

    @PreAuthorize("@ss.hasPermi('coin:cointype:add')")
    @Log(title = "币种类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CoinType coinType)
    {
        return toAjax(coinTypeService.insertCoinType(coinType));
    }

    @PreAuthorize("@ss.hasPermi('coin:cointype:edit')")
    @Log(title = "币种类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CoinType coinType)
    {
        return toAjax(coinTypeService.updateCoinType(coinType));
    }

    @PreAuthorize("@ss.hasPermi('coin:cointype:remove')")
    @Log(title = "币种类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(coinTypeService.deleteCoinTypeByIds(ids));
    }
}
