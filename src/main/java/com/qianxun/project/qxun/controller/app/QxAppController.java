package com.qianxun.project.qxun.controller.app;

import java.util.List;

import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qianxun.common.utils.StringUtils;
import com.qianxun.framework.aspectj.lang.annotation.Log;
import com.qianxun.framework.aspectj.lang.enums.BusinessType;
import com.qianxun.framework.web.controller.BaseController;
import com.qianxun.framework.web.domain.AjaxResult;
import com.qianxun.framework.web.page.TableDataInfo;
import com.qianxun.project.coin.service.ICoinAccountService;
import com.qianxun.project.qxun.domain.QxGiftRec;
import com.qianxun.project.qxun.domain.QxLayerRec;
import com.qianxun.project.qxun.domain.QxShareRec;
import com.qianxun.project.qxun.domain.QxUserAcc;
import com.qianxun.project.qxun.domain.QxVipRec;
import com.qianxun.project.qxun.domain.vo.BuyVo;
import com.qianxun.project.qxun.service.IQxGiftCfgService;
import com.qianxun.project.qxun.service.IQxGiftRecService;
import com.qianxun.project.qxun.service.IQxLayerCfgService;
import com.qianxun.project.qxun.service.IQxLayerRecService;
import com.qianxun.project.qxun.service.IQxShareRecService;
import com.qianxun.project.qxun.service.IQxUserAccService;
import com.qianxun.project.qxun.service.IQxVipCfgService;
import com.qianxun.project.qxun.service.IQxVipRecService;
import com.qianxun.project.system.mapper.UserTeamMapper;

@RestController
@RequestMapping("/qx/app")
public class QxAppController extends BaseController {
    @Autowired
    private IQxGiftRecService qxGiftRecService;

    @Autowired
    private IQxUserAccService qxUserAccService;

    @Autowired
    private IQxShareRecService qxShareRecService;

    @Autowired
    private IQxVipRecService qxVipRecService;

    @Autowired
    private IQxLayerRecService qxLayerRecService;

    /**
     * 购买礼包
     */
    @PostMapping(value = "/buy")
    @Log(title = "购买礼包", businessType = BusinessType.INSERT)
    public AjaxResult buy(@RequestBody BuyVo buyVo) {

        qxGiftRecService.buy(getUserId(), buyVo);
        return AjaxResult.success();
    }

    //    2.查询用户礼包等级+VIP等级
    @GetMapping(value = "/userAcc")
    public AjaxResult userAcc() {
        QxUserAcc userAcc = qxUserAccService.selectQxUserAccByUserId(getUserId());
        if (StringUtils.isNull(userAcc)) {
            userAcc = new QxUserAcc();
            userAcc.setUserId(getUserId());
            userAcc.setGiftGrade(0L);
            userAcc.setVipGrade(0L);
        }
        return AjaxResult.success(userAcc);
    }

    //    3.礼包购买记录
    @GetMapping("buyGiftRec")
    public TableDataInfo buyGiftRec() {
        QxGiftRec rec = new QxGiftRec();
        rec.setUserId(getUserId());
        startPage();
        List<QxGiftRec> list = qxGiftRecService.selectQxGiftRecList(rec);
        return getDataTable(list);
    }

    //    4.层奖记录
    @GetMapping("layerRec")
    public TableDataInfo layerRec() {
        QxLayerRec rec = new QxLayerRec();
        rec.setUserId(getUserId());
        startPage();
        List<QxLayerRec> list = qxLayerRecService.selectQxLayerRecList(rec);
        return getDataTable(list);
    }

    //    5.分享奖记录
    @GetMapping("shareRec")
    public TableDataInfo shareRec() {
        QxShareRec rec = new QxShareRec();
        rec.setUserId(getUserId());
        startPage();
        List<QxShareRec> list = qxShareRecService.selectQxShareRecList(rec);
        return getDataTable(list);
    }

    //    6.VIP奖记录
    @GetMapping("vipRec")
    public TableDataInfo vipRec() {
        QxVipRec rec = new QxVipRec();
        rec.setUserId(getUserId());
        startPage();
        List<QxVipRec> list = qxVipRecService.selectQxVipRecList(rec);
        return getDataTable(list);
    }

    // 7.礼包列表
    @GetMapping("gifList")
    public AjaxResult gifList(){
        List<QxGiftRec> qxGiftRecs = qxGiftRecService.selectQxGiftRecList(new QxGiftRec());
        return AjaxResult.success(qxGiftRecs);
    }


}

