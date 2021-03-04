package com.qianxun.project.coin.controller.app;


import java.util.List;

import com.alibaba.fastjson.JSON;
import com.qianxun.common.enums.CoinConstants;
import com.qianxun.common.exception.user.ParameterException;
import com.qianxun.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qianxun.common.exception.user.NoPermissionException;
import com.qianxun.framework.aspectj.lang.annotation.Log;
import com.qianxun.framework.aspectj.lang.enums.BusinessType;
import com.qianxun.framework.web.controller.BaseController;
import com.qianxun.framework.web.domain.AjaxResult;
import com.qianxun.framework.web.page.TableDataInfo;
import com.qianxun.project.coin.domain.CoinAccount;
import com.qianxun.project.coin.domain.CoinRecord;
import com.qianxun.project.coin.domain.CoinType;
import com.qianxun.project.coin.domain.CoinWallet;
import com.qianxun.project.coin.service.ICoinAccountService;
import com.qianxun.project.coin.service.ICoinRecordService;
import com.qianxun.project.coin.service.ICoinTypeService;
import com.qianxun.project.coin.service.ICoinWalletService;

/**
 * 资产App接口
 */
@RestController
@RequestMapping("/coin/app")
public class CoinController extends BaseController {
    @Autowired
    private ICoinAccountService coinAccountService;
    
    @Autowired
    private ICoinTypeService coinTypeService;
    
    @Autowired
    private ICoinRecordService coinRecordService;
    
    @Autowired
    private ICoinWalletService coinWalletService;

    /**
     * 查询列表类
     * 
     * @Description: 查询所有币种类型列表接口
     * @Author:
     * @Date: 2021/1/11 11:39
     * @return: com.ht.framework.web.page.TableDataInfo
     **/
    @GetMapping("coinTypeList")
    public TableDataInfo coinTypeList() {
        startPage();
        List<CoinType> list = coinTypeService.selectCoinTypeList(null);
        return getDataTable(list);
    }

    /**
     * 查询单个数据类
     * 
     * @Description: 查询单个币种类型
     * @Author:
     * @Date: 2021/1/11 11:39
     * @param id:
     * @return: com.ht.framework.web.domain.AjaxResult
     **/
    @GetMapping(value = "coinType/{id}")
    public AjaxResult coinType(@PathVariable("id") Long id) {
        return AjaxResult.success(coinTypeService.selectCoinTypeById(id));
    }

    /**
     * @Description: 查询资产账户列表
     * @Author:
     * @Date: 2021/1/9 17:21
     * @param :
     * @return: com.ht.framework.web.page.TableDataInfo
     **/
    @GetMapping("coinAccountList")
    public TableDataInfo coinAccountList() {
        startPage();
        return getDataTable(coinAccountService.selectCoinAccountByUserId(getUserId()));
    }

    /**
     * @Description: 查询资产账户详情
     * @Author:
     * @Date: 2021/1/9 17:21
     * @param :
     * @return: com.ht.framework.web.domain.AjaxResult
     **/
    @GetMapping("coinAccount/{id}")
    public AjaxResult coinAccount(@PathVariable Long id) {
        CoinAccount acc = coinAccountService.selectCoinAccountById(id);
        if (acc.getUserId().longValue() != getUserId()) {
             throw new NoPermissionException();
        }
        return AjaxResult.success(acc);
    }

    /**
     * @Description: 查询流水记录列表
     * @Author:
     * @Date: 2021/1/9 17:21
     * @param :
     * @return: com.ht.framework.web.page.TableDataInfo
     **/
    @GetMapping("coinRecordList")
    public TableDataInfo coinRecordList() {
        CoinRecord coinRecord = new CoinRecord();
        coinRecord.setUserId(getUserId());
        startPage();
        List<CoinRecord> list = coinRecordService.selectCoinRecordList(coinRecord);
        return getDataTable(list);
    }

    /**
     * @Description: 查询流水记录详情
     * @Author:
     * @Date: 2021/1/11 14:26
     * @param id:
     * @return: com.ht.framework.web.domain.AjaxResult
     **/
    @GetMapping("coinRecord/{id}")
    public AjaxResult coinRecord(@PathVariable Long id) {
        CoinRecord cr = coinRecordService.selectCoinRecordById(id);
        if (cr.getUserId().longValue() != getUserId()) {
            throw new NoPermissionException();
        }
        return AjaxResult.success(cr);
    }

    /**
     * @Description: 指定币种充值地址列表
     * @Author:
     * @Date: 2021/1/11 9:58
     * @return: com.ht.framework.web.page.TableDataInfo
     **/
    @GetMapping("coinFromAddressList/{id}")
    public TableDataInfo coinFromAddressList(@PathVariable Long id) {
        startPage();
        List<CoinWallet> list = coinWalletService.selectCoinWalletByCoinIdListToUdun(id,getUserId());
        return getDataTable(list);
    }


    /**
     * 提现
     * @param coinRecord
     * @return
     */
    @Log(title = "发起提现", businessType = BusinessType.INSERT)
    @PostMapping("withdraw")
    public AjaxResult withdraw(@RequestBody CoinRecord coinRecord){
        coinRecord.setUserId(getUserId());
        coinWalletService.withdraw(coinRecord);
        return AjaxResult.success();
    }


    /**
     * @Description: 查询提现/充值记录列表
     * @Author:
     * @Date: 2021/1/15 13:01
     * @return: com.ht.framework.web.domain.AjaxResult
     **/
    @GetMapping("withdrawRecordList/{mainType}")
    public TableDataInfo withdrawRecordList(@PathVariable String mainType){
        startPage();
        if (StringUtils.isEmpty(mainType)||
                (!CoinConstants.MainType.RECHARGE.getType().equals(mainType)&&!CoinConstants.MainType.WITHDRAWAL.getType().equals(mainType))){
            logger.error("查询提现/充值参数主类型异常{}", JSON.toJSON(mainType));
            throw new ParameterException();
        }
        CoinRecord record = new CoinRecord();
        record.setMainType(mainType);
        record.setUserId(getUserId());
        //提现记录列表
        List<CoinRecord> list = coinRecordService.selectCoinRecordList(record);
        return getDataTable(list);
    }




}
