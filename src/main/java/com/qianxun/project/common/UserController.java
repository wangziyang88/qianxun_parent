package com.qianxun.project.common;

import java.io.IOException;

import com.qianxun.common.constant.UserConstants;
import com.qianxun.framework.security.*;
import com.qianxun.project.system.domain.SysConfig;
import com.qianxun.project.system.service.ISysConfigService;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.qianxun.common.constant.Constants;
import com.qianxun.common.utils.FileUploadUtils;
import com.qianxun.common.utils.MessageUtils;
import com.qianxun.common.utils.SecurityUtils;
import com.qianxun.common.utils.ServletUtils;
import com.qianxun.framework.aspectj.lang.annotation.Log;
import com.qianxun.framework.aspectj.lang.enums.BusinessType;
import com.qianxun.framework.config.HtConfig;
import com.qianxun.framework.security.service.TokenService;
import com.qianxun.framework.web.controller.BaseController;
import com.qianxun.framework.web.domain.AjaxResult;
import com.qianxun.project.common.service.UserInfoService;
import com.qianxun.project.system.domain.SysUser;
import com.qianxun.project.system.service.ISysUserService;


/**
 *   用户接口
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    
    @Autowired
    private UserInfoService userInfoService;
    
    @Autowired
    private ISysUserService userService;
    
    @Autowired
    private TokenService tokenService;


    @Autowired
    private ISysConfigService configService;
    /**
     * @Description: 普通用户注册方法
     * @Author:
     * @Date: 2021/2/26 13:58
     * @return: null
     **/
    @PostMapping("/register")
    public AjaxResult register(@RequestBody @Validated RegisterBody registerBody) {
        //验证手机或者邮箱号是否正确
        checkFormat(registerBody.getCaptchaType(),registerBody.getEmail(),registerBody.getPhonenumber());
        SysUser user = userInfoService.register(registerBody);
        return AjaxResult.success(MessageUtils.message("user.register.success"),user.getUserName());//注册成功
    }
    /**
     * @Description: 普通用户登录方法
     * @Author:
     * @Date: 2021/1/9 11:07
     * @param loginBody:   登录信息
     * @return: com.ht.framework.web.domain.AjaxResult
     **/
    @PostMapping("/login")
    public AjaxResult login(@RequestBody @Validated LoginBody loginBody) {
        System.out.println(loginBody);
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = userInfoService.login(loginBody);
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * @Description: 发送验证码
     * @Author:
     * @Date: 2021/1/9 11:03
     * @param captchaBody:
     * @return: com.ht.framework.web.domain.AjaxResult
     **/
    @PostMapping("/sendCaptchaCode")
    public AjaxResult sendCaptchaCode(@RequestBody @Validated CaptchaBody captchaBody){
        checkFormat(captchaBody.getCaptchaType(),captchaBody.getEmail(),captchaBody.getPhonenumber());
        String uuid = userInfoService.sendCaptchaCode(captchaBody);
        return AjaxResult.success(MessageUtils.message("user.jcaptcha.send.success"),uuid);//验证码发送成功
    }

    /**
     * 用户修改密码
     */
    /**
     * @Description: 修改密码
     * @Author:
     * @Date: 2021/2/27 17:12
     * @return: null
     **/
    @Log(title = "用户修改密码", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/changePassword")
    public AjaxResult changePassword(@RequestBody @Validated PasswordBodyVo passwordBodyVo) {
        //判断安全密码是否正确
        checkSecurityPassword(passwordBodyVo.getSecurityPassword());

        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());

        String userName = loginUser.getUsername();
        String password = loginUser.getPassword();
        if (!SecurityUtils.matchesPassword(passwordBodyVo.getOldPassword(), password)) {
            return AjaxResult.error("user.wrong.old.password");//旧密码输入错误
        }
        if (SecurityUtils.matchesPassword(passwordBodyVo.getNewPassword(), password)) {
            return AjaxResult.error(MessageUtils.message("user.old.new.password.cannot.same"));//新旧密码不能相同
        }
        if (userService.resetUserPwd(userName, SecurityUtils.encryptPassword(passwordBodyVo.getNewPassword())) > 0) {
            // 更新缓存用户密码
            loginUser.getUser().setPassword(SecurityUtils.encryptPassword(passwordBodyVo.getNewPassword()));
            tokenService.setLoginUser(loginUser);
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

    /**
     * @Description: 修改安全密码
     * @Author:
     * @Date: 2021/2/27 17:12
     * @return: null
     **/
    @Log(title = "修改安全密码", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/changeSecurityPassword")
    public AjaxResult changeSecurityPassword(@RequestBody @Validated SecurityPwdBodyVo securityPwdBodyVo) {
        //判断旧安全密码是否正确
        checkSecurityPassword(securityPwdBodyVo.getOldSecurityPwd());
        LoginUser loginUser = getLoginUser();
        String userName = loginUser.getUsername();

        if (  userService.resetSecurityPwd(userName,securityPwdBodyVo.getNewSecurityPwd()) > 0) {
            // 更新缓存用户密码
            loginUser.getUser().setSecurityPassword(SecurityUtils.encryptPassword(securityPwdBodyVo.getNewSecurityPwd()));
            tokenService.setLoginUser(loginUser);
            return AjaxResult.success();
        }
        //更新缓存中的安全密码
        return AjaxResult.error();
    }



    /**
     * @Description: 找回密码
     * @Author:
     * @Date: 2021/1/11 15:27
     * @return: com.ht.framework.web.domain.AjaxResult
     **/
    @Log(title = "用户找回密码", businessType = BusinessType.UPDATE)
    @PostMapping("retrievePassword")
    public AjaxResult retrievePassword(@RequestBody @Validated RetrievePasswordBody retrievePasswordBody){
        checkFormat( retrievePasswordBody.getCaptchaType(), retrievePasswordBody.getEmail(), retrievePasswordBody.getPhonenumber());
        userInfoService.retrievePassword(retrievePasswordBody);
        return AjaxResult.success(MessageUtils.message("user.retrievePassword.success"));// 密码找回成功
    }
    /**
     * @Description:    判断用户名是否存在,给与实时返回
     * @Author:
     * @Date: 2021/1/23 11:09
     * @return: com.ht.framework.web.domain.AjaxResult
     **/
    @GetMapping("/checkUserName/{userName}")
    public AjaxResult checkUserName(@PathVariable String userName){
        String s = userService.checkUserNameUnique(userName);
        if (UserConstants.NOT_UNIQUE.equals(s)){
            return AjaxResult.error(MessageUtils.message("user.hotelName.exists"));
        }
        return AjaxResult.success();
    }


    /**
     * @Description: 获取版本号
     * @Author:
     * @Date: 2021/1/26 12:52
     * @return: com.ht.framework.web.domain.AjaxResult
     **/
    @GetMapping("getNewVersion")
    public AjaxResult getNewVersion (){
        VersionVo versionVo=new VersionVo();
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String os = userAgent.getOperatingSystem().getName();

        if ("android".equals(os.toLowerCase())){
            SysConfig androidVersionConfig = configService.selectConfigById(4L);//安卓版本号config
            SysConfig androidUrlConfig = configService.selectConfigById(5L);//安卓最新版本下载config

            versionVo.setVersionNum(androidVersionConfig.getConfigValue());//安卓最新版本号
            versionVo.setVersionUrl(androidUrlConfig.getConfigValue());//安卓最新下载地址
            versionVo.setUpdateTime(androidVersionConfig.getUpdateTime());//最新地址更新时间

        }else if ("ios".equals(os.toLowerCase()) ||"Mac OS X (iPhone)".equals(os)){
            SysConfig androidVersionConfig = configService.selectConfigById(6L);//IOS版本号config
            SysConfig androidUrlConfig = configService.selectConfigById(7L);//IOS最新版本下载config

            versionVo.setVersionNum(androidVersionConfig.getConfigValue());//IOS最新版本号
            versionVo.setVersionUrl(androidUrlConfig.getConfigValue());//IOS最新下载地址
            versionVo.setUpdateTime(androidVersionConfig.getUpdateTime());//最新地址更新时间
        }
        return AjaxResult.success(versionVo);
    }




    /**
     * 用户修改头像
     */
    @Log(title = "用户修改头像", businessType = BusinessType.UPDATE)
    @PostMapping("/avatar")
    public AjaxResult avatar(@RequestParam("avatarfile") MultipartFile file) throws IOException{
        if (!file.isEmpty()){
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String avatar = FileUploadUtils.upload(HtConfig.getAvatarPath(), file);

            if (userService.updateUserAvatar(loginUser.getUsername(), avatar)) {
                // 更新缓存用户头像
                loginUser.getUser().setAvatar(avatar);
                tokenService.setLoginUser(loginUser);
                return AjaxResult.success(MessageUtils.message("user.upload.successfully"),avatar);//上传成功
            }
        }
        return AjaxResult.error(MessageUtils.message("user.upload.abnormal.please.try.again.later"));//"上传图片异常，请稍后再试"
    }


    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        user.setPassword("");// 过滤密码
        return AjaxResult.success(user);
    }

    /**
     * 用户修改个人信息
     */
    @Log(title = "用户修改个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("updateProfile")
    public AjaxResult updateProfile(@RequestBody SysUser user)
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser userNow = loginUser.getUser();
        SysUser u = new SysUser();
        u.setUserId(userNow.getUserId());
        u.setNickName(user.getNickName());
        u.setSex(user.getSex());
        if (userService.updateUserProfile(u) > 0){
            // 更新缓存用户信息
            loginUser.getUser().setNickName(user.getNickName());// 昵称
            loginUser.getUser().setSex(user.getSex());// 性别 0=男,1=女,2=未知
            tokenService.setLoginUser(loginUser);
            return AjaxResult.success();
        }

        return AjaxResult.error();
    }

}
