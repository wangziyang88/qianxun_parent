package com.qianxun.project.coin.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.qianxun.framework.aspectj.lang.annotation.Log;
import com.qianxun.framework.aspectj.lang.enums.BusinessType;
import com.qianxun.project.coin.domain.CoinAccount;
import com.qianxun.project.coin.service.ICoinAccountService;
import com.qianxun.framework.web.controller.BaseController;
import com.qianxun.framework.web.domain.AjaxResult;
import com.qianxun.common.utils.ExcelUtil;
import com.qianxun.framework.web.page.TableDataInfo;

@RestController
@RequestMapping("/coin/coinaccount")
public class CoinAccountController extends BaseController
{
    @Autowired
    private ICoinAccountService coinAccountService;

    @PreAuthorize("@ss.hasPermi('coin:coinaccount:list')")
    @GetMapping("/list")
    public TableDataInfo list(CoinAccount coinAccount)
    {
        startPage();
        List<CoinAccount> list = coinAccountService.selectCoinAccountList(coinAccount);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('coin:coinaccount:export')")
    @Log(title = "资产余额", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CoinAccount coinAccount)
    {
        List<CoinAccount> list = coinAccountService.selectCoinAccountList(coinAccount);
        ExcelUtil<CoinAccount> util = new ExcelUtil<CoinAccount>(CoinAccount.class);
        return util.exportExcel(list, "coinaccount");
    }

    @PreAuthorize("@ss.hasPermi('coin:coinaccount:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(coinAccountService.selectCoinAccountById(id));
    }

    @PreAuthorize("@ss.hasPermi('coin:coinaccount:edit')")
    @Log(title = "资产余额", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CoinAccount coinAccount)
    {
        return toAjax(coinAccountService.updateCoinAccount(coinAccount));
    }

}
