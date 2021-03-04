package com.qianxun.project.coin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qianxun.common.utils.ExcelUtil;
import com.qianxun.framework.aspectj.lang.annotation.Log;
import com.qianxun.framework.aspectj.lang.enums.BusinessType;
import com.qianxun.framework.web.controller.BaseController;
import com.qianxun.framework.web.domain.AjaxResult;
import com.qianxun.framework.web.page.TableDataInfo;
import com.qianxun.project.coin.domain.CoinWallet;
import com.qianxun.project.coin.service.ICoinWalletService;

@RestController
@RequestMapping("/coin/coinwallet")
public class CoinWalletController extends BaseController
{
    @Autowired
    private ICoinWalletService coinWalletService;

    @PreAuthorize("@ss.hasPermi('coin:coinwallet:list')")
    @GetMapping("/list")
    public TableDataInfo list(CoinWallet coinWallet)
    {
        startPage();
        List<CoinWallet> list = coinWalletService.selectCoinWalletList(coinWallet);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('coin:coinwallet:export')")
    @Log(title = "钱包地址", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CoinWallet coinWallet)
    {
        List<CoinWallet> list = coinWalletService.selectCoinWalletList(coinWallet);
        ExcelUtil<CoinWallet> util = new ExcelUtil<CoinWallet>(CoinWallet.class);
        return util.exportExcel(list, "coinwallet");
    }

    @PreAuthorize("@ss.hasPermi('coin:coinwallet:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(coinWalletService.selectCoinWalletById(id));
    }

    @PreAuthorize("@ss.hasPermi('coin:coinwallet:edit')")
    @Log(title = "钱包地址", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CoinWallet coinWallet)
    {
        return toAjax(coinWalletService.updateCoinWallet(coinWallet));
    }

}
