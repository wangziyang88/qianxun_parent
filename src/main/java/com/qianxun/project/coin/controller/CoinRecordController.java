package com.qianxun.project.coin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qianxun.common.utils.ExcelUtil;
import com.qianxun.framework.aspectj.lang.annotation.Log;
import com.qianxun.framework.aspectj.lang.enums.BusinessType;
import com.qianxun.framework.web.controller.BaseController;
import com.qianxun.framework.web.domain.AjaxResult;
import com.qianxun.framework.web.page.TableDataInfo;
import com.qianxun.project.coin.domain.CoinRecord;
import com.qianxun.project.coin.service.ICoinRecordService;

@RestController
@RequestMapping("/coin/coinrecord")
public class CoinRecordController extends BaseController
{
    @Autowired
    private ICoinRecordService coinRecordService;

    @PreAuthorize("@ss.hasPermi('coin:coinrecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(CoinRecord coinRecord)
    {
        startPage();
        List<CoinRecord> list = coinRecordService.selectCoinRecordList(coinRecord);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('coin:coinrecord:export')")
    @Log(title = "资产记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CoinRecord coinRecord)
    {
        List<CoinRecord> list = coinRecordService.selectCoinRecordList(coinRecord);
        ExcelUtil<CoinRecord> util = new ExcelUtil<CoinRecord>(CoinRecord.class);
        return util.exportExcel(list, "coinrecord");
    }

    @PreAuthorize("@ss.hasPermi('coin:coinrecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(coinRecordService.selectCoinRecordById(id));
    }

}
