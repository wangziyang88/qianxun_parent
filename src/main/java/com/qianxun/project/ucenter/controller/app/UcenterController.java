package com.qianxun.project.ucenter.controller.app;

import com.qianxun.common.utils.*;
import com.qianxun.framework.aspectj.lang.annotation.Log;
import com.qianxun.framework.aspectj.lang.enums.BusinessType;
import com.qianxun.framework.security.LoginUser;
import com.qianxun.framework.security.PasswordBodyVo;
import com.qianxun.framework.security.SecurityPwdBodyVo;
import com.qianxun.framework.security.service.TokenService;
import com.qianxun.framework.web.controller.BaseController;
import com.qianxun.framework.web.domain.AjaxResult;
import com.qianxun.framework.web.page.TableDataInfo;
import com.qianxun.project.system.domain.SysNotice;
import com.qianxun.project.system.domain.SysUser;
import com.qianxun.project.system.service.ISysNoticeService;
import com.qianxun.project.system.service.ISysUserService;
import com.qianxun.project.ucenter.domain.QianxunUserAlipay;
import com.qianxun.project.ucenter.domain.QianxunUserBank;
import com.qianxun.project.ucenter.domain.QianxunUserExternal;
import com.qianxun.project.ucenter.domain.QianxunUserWechat;
import com.qianxun.project.ucenter.service.IQianxunUserAlipayService;
import com.qianxun.project.ucenter.service.IQianxunUserBankService;
import com.qianxun.project.ucenter.service.IQianxunUserExternalService;
import com.qianxun.project.ucenter.service.IQianxunUserWechatService;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @Author:
 * @Email:
 * @Description:
 * @Date:2021/2/27 14:19
 * @Version:1.0
 */
@RestController
@RequestMapping("/qianxun/ucenter")
public class UcenterController extends BaseController {
    @Autowired
    private IQianxunUserWechatService wechatService;

    @Autowired
    private IQianxunUserAlipayService alipayService;

    @Autowired
    private IQianxunUserExternalService externalService;

    @Autowired
    private IQianxunUserBankService bankService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysNoticeService noticeService;

    /**
     * @Description: 查看微信付款方式
     * @Author:
     * @Date: 2021/2/27 12:58
     * @return: com.qianxun.framework.web.domain.AjaxResult
     **/
    @GetMapping(value = "/getUserWechat")
    public AjaxResult getUserWechat() {
        return AjaxResult.success(wechatService.selectByUserId(getUserId()));
    }

    /**
     * @param wechat:
     * @Description: 修改/添加微信付款方式
     * @Author:
     * @Date: 2021/2/27 12:58
     * @return: com.qianxun.framework.web.domain.AjaxResult
     **/
    @Log(title = "修改/添加微信付款方式", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/updateOrAddUserWechat")
    public AjaxResult updateOrAddUserWechat(@RequestBody @Validated QianxunUserWechat wechat) {
        //判断安全密码是否正确
        checkSecurityPassword((String) wechat.getParams().get("securityPassword"));
        //如果提交的有id 则表示为修改信息
        if (StringUtils.isNotNull(wechat.getId())) {
            wechatService.updateByPrimaryKey(wechat);
        } else {
            wechat.setUserId(getUserId());
            wechat.setCreateTime(DateUtils.getNowDate());
            wechatService.insert(wechat);
        }
        return AjaxResult.success();
    }

    /**
     * @Description: 查看支付宝付款方式
     * @Author:
     * @Date: 2021/2/27 12:58
     * @return: com.qianxun.framework.web.domain.AjaxResult
     **/
    @GetMapping(value = "/getUserAlipay")
    public AjaxResult getUserAlipay() {
        return AjaxResult.success(alipayService.selectByUserId(getUserId()));
    }

    /**
     * @param alipay:
     * @Description: 修改/添加支付宝付款方式
     * @Author:
     * @Date: 2021/2/27 12:58
     * @return: com.qianxun.framework.web.domain.AjaxResult
     **/
    @Log(title = "修改/添加支付宝付款方式", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/updateOrAddUserAlipay")
    public AjaxResult updateOrAddUserAlipay(@RequestBody @Validated QianxunUserAlipay alipay) {
        //判断安全密码是否正确
        checkSecurityPassword((String) alipay.getParams().get("securityPassword"));
        //如果提交的有id 则表示为修改信息
        if (StringUtils.isNotNull(alipay.getId())) {
            alipayService.updateByPrimaryKey(alipay);
        } else {
            alipay.setUserId(getUserId());
            alipay.setCreateTime(DateUtils.getNowDate());
            alipayService.insert(alipay);
        }
        return AjaxResult.success();
    }

    /**
     * @Description: 查看外部地址付款方式
     * @Author:
     * @Date: 2021/2/27 12:58
     * @return: com.qianxun.framework.web.domain.AjaxResult
     **/
    @GetMapping(value = "/getUserExternal")
    public AjaxResult getUserExternal() {
        return AjaxResult.success(externalService.selectByUserId(getUserId()));
    }


    /**
     * @param external:
     * @Description: 修改外部地址付款方式
     * @Author:
     * @Date: 2021/2/27 12:58
     * @return: com.qianxun.framework.web.domain.AjaxResult
     **/
    @Log(title = "修改/添加外部地址付款方式", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/updateOrAddUserExternal")
    public AjaxResult updateOrAddUserExternal(@RequestBody @Validated QianxunUserExternal external) {
        //判断安全密码是否正确
        checkSecurityPassword((String) external.getParams().get("securityPassword"));
        //如果提交的有id 则表示为修改信息
        if (StringUtils.isNotNull(external.getId())) {
            externalService.updateByPrimaryKey(external);
        } else {
            external.setUserId(getUserId());
            external.setCreateTime(DateUtils.getNowDate());
            externalService.insert(external);
        }
        return AjaxResult.success();
    }


    /**
     * @Description: 查看银行付款方式
     * @Author:
     * @Date: 2021/2/27 12:58
     * @return: com.qianxun.framework.web.domain.AjaxResult
     **/
    @GetMapping(value = "/getUserBank")
    public AjaxResult getUserBank() {
        return AjaxResult.success(bankService.selectByUserId(getUserId()));
    }


    /**
     * @param bank:
     * @Description: 修改/添加银行付款方式
     * @Author:
     * @Date: 2021/2/27 12:58
     * @return: com.qianxun.framework.web.domain.AjaxResult
     **/
    @Log(title = "修改/添加银行付款方式", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/updateOrAddUserBank")
    public AjaxResult updateUserBank(@RequestBody @Validated QianxunUserBank bank) {
        //判断安全密码是否正确
        checkSecurityPassword((String) bank.getParams().get("securityPassword"));
        //如果提交的有id 则表示为修改信息
        if (StringUtils.isNotNull(bank.getId())) {
            bankService.updateByPrimaryKey(bank);
        } else {
            bank.setUserId(getUserId());
            bank.setCreateTime(DateUtils.getNowDate());
            bankService.insert(bank);
        }
        return AjaxResult.success();
    }


    /**
     * @Description: 通过用户id查询所有支付方式
     * @Author:
     * @Date: 2021/2/27 12:58
     * @return: com.qianxun.framework.web.domain.AjaxResult
     **/
    @GetMapping(value = "/getPayMethodBy/{userId}")
    public AjaxResult getPayMethodBy(@PathVariable Long userId) {
        QianxunUserBank bank = bankService.selectByUserId(userId);
        QianxunUserWechat wechat = wechatService.selectByUserId(userId);
        QianxunUserAlipay alipay = alipayService.selectByUserId(userId);
        QianxunUserExternal external = externalService.selectByUserId(userId);
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("bank", bank);
        hashMap.put("wechat", wechat);
        hashMap.put("alipay", alipay);
        hashMap.put("external", external);
        return AjaxResult.success(hashMap);
    }

    /**
     * 公告列表
     */
    @GetMapping(value = "getNoticeList")
    public TableDataInfo getNoticeList(){
        startPage();
        return getDataTable(noticeService.selectNoticeList(null));
    }


    /**
     * @Description: 我的组织
     * @Author:
     * @Date: 2021/2/27 17:12
     * @return: null
     **/
    @GetMapping(value = "/getTeamList")
    public AjaxResult getTeamList() {
        SysUser sysUser = new SysUser();
        sysUser.setParentId(getUserId());
        List<SysUser> sysUsers = userService.selectUserList(sysUser);
        return AjaxResult.success(sysUsers);
    }

}

