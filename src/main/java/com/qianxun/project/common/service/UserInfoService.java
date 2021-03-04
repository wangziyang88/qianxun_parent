package com.qianxun.project.common.service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.qianxun.common.enums.UserType;
import com.qianxun.common.exception.user.*;
import com.qianxun.common.utils.*;
import com.qianxun.framework.security.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.qianxun.common.constant.Constants;
import com.qianxun.common.constant.UserConstants;
import com.qianxun.common.exception.BaseException;
import com.qianxun.common.exception.CustomException;
import com.qianxun.framework.manager.AsyncManager;
import com.qianxun.framework.manager.factory.AsyncFactory;
import com.qianxun.framework.redis.RedisCache;
import com.qianxun.framework.security.service.TokenService;
import com.qianxun.project.system.domain.SysUser;
import com.qianxun.project.system.service.ISysUserService;


/**
 * 用户登录校验方法
 *
 * @author live
 */
@Component
public class UserInfoService {

    protected final Logger log = LoggerFactory.getLogger(UserInfoService.class);

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ISysUserService userService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private EmailService emailService;

    /**
     * 用户注册 邮箱/手机号
     * @param registerBody 注册实体
     * @return 返回注册结果
     */
    public SysUser register(RegisterBody registerBody) {
        SysUser user = new SysUser();//要返回的对象

        String captchaType = registerBody.getCaptchaType();//注册方式
        String email = registerBody.getEmail();//邮箱号
        String phonenumber = registerBody.getPhonenumber();//电话号码
        String userName = registerBody.getUserName();//用户名
        String password = registerBody.getPassword();//登录密码
        String securityPassword = registerBody.getSecurityPassword();//安全密码
        String bodyInviteCode = registerBody.getInviteCode();//推荐码

        //用户名不能为空
        if (StringUtils.isEmpty(userName)) {
            throw new BaseException(MessageUtils.message("user.name.is.empty"));//用户名不能为空
        } else {
            //判断用户名是否存在
            String s = userService.checkUserNameUnique(userName);
            if (StringUtils.isNotEmpty(s) && UserConstants.NOT_UNIQUE.equals(s)) {
                throw new BaseException(MessageUtils.message("user.name.is.exists"));//用户已存在
            }

            /**注册的用户名不能等于其他用的邮箱名  因为可以邮箱和用户名登录*/
            SysUser sysUser = new SysUser();
            sysUser.setEmail(userName);
            String s1 = userService.checkEmailUnique(sysUser);
            if (StringUtils.isNotEmpty(s1) && UserConstants.NOT_UNIQUE.equals(s1)) {
                throw new BaseException(MessageUtils.message("user.name.is.exists"));
            }

            user.setUserName(userName);
        }
        // 验证 手机/邮箱 的验证码
        String verifyKey = Constants.CAPTCHA_EMAILORPHONE_CODE_KEY + ":" + registerBody.getUuid();
        //获得缓存数据  包括手机号或邮箱   验证码  验证次数
        String value = redisCache.getCacheObject(verifyKey);
        if (StringUtils.isEmpty(value)) {
            throw new CaptchaExpireException();//验证码已失效
        }
        String emailOrPhone = value.split("\\|")[0];// 截取缓存中手机号或邮箱
        String code = value.split("\\|")[1];// 截取缓存中验证码
        int num = Integer.parseInt(value.split("\\|")[2]);// 截取缓存中已验证次数

        if (Constants.EMAIL.equals(captchaType)) {
            if (!emailOrPhone.equals(email)) {
                throw new BaseException(MessageUtils.message("user.email.error")); //邮箱错误
            }
            user.setEmail(email);
            user.setCreateBy(email);
        } else if (Constants.PHONE_NUMBER.equals(captchaType)) {
            if (!emailOrPhone.equals(phonenumber)) {
                throw new BaseException(MessageUtils.message("user.mobile.phone.number.error"));//手机错误
            }

            user.setPhonenumber(phonenumber);
            user.setCreateBy(phonenumber);
        } else {
            throw new BaseException(MessageUtils.message("user.no.registration.type"));//未指定类型
        }

        if (num > 1) {
            redisCache.setCacheObject(verifyKey, emailOrPhone + "|" + code + "|" + (num - 1), Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        } else {
            redisCache.deleteObject(verifyKey);// 最后一次直接失效
        }
        if (!code.equals(registerBody.getCode())) {// 判断验证码
            throw new UserException("user.jcaptcha.error", null);// 验证码错误
        }

        Long inviteCode = 0L;
        // 验证邀请人是否正确存在
        if (StringUtils.isNotEmpty(bodyInviteCode) && !"0".equals(bodyInviteCode)) {
            try {
                inviteCode = Long.parseLong(bodyInviteCode);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                throw new UserException("user.inviteCode.error", null);// 邀请码错误
            }
            SysUser inviteUser = userService.selectUserById(inviteCode);

            if (StringUtils.isNull(inviteUser) || UserType.SYSTEMUSER.getCode().equals(inviteUser.getUserType())) {
                throw new UserException("user.inviteCode.error", null);// 邀请码错误
            }
        }

        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName()))) {
            throw new BaseException(MessageUtils.message("user.is.exists")); //账号已存在
        } else if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            throw new BaseException(MessageUtils.message("user.is.exists")); //账号已存在
        } else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            throw new BaseException(MessageUtils.message("user.is.exists")); //账号已存在
        }
        user.setUserType("01");// 普通用户
        user.setParentId(inviteCode);// 邀请人 0表示没有邀请人
        user.setPassword(SecurityUtils.encryptPassword(password));
        user.setSecurityPassword(SecurityUtils.encryptPassword(securityPassword));
        user.setCreateTime(DateUtils.getNowDate());
        user.setRemark("普通用户");
        userService.insertUser(user);
        redisCache.deleteObject(verifyKey);// 成功直接失效
        return user;
    }
    /**
     * 用户登录验证
     *
     * @param loginBody: 登录数据
     * @return 结果
     */
    public String login(LoginBody loginBody) {
        // 用户验证
        Authentication authentication = null;
        SysUser user = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginBody.getUsername(), loginBody.getPassword()));
            // 判断用户类型
            user = userService.selectUserByUserName(loginBody.getUsername());

            //可以使用email登录
            if (StringUtils.isNull(user)){
                user = userService.selectUserByEmail(loginBody.getUsername());
            }
            if (UserType.SYSTEMUSER.getCode().equals(user.getUserType())) {
                log.info("登录用户：{} 是管理员.只能用于登录后台管理系统", loginBody.getUsername());
                throw new BaseException(MessageUtils.message("administrator.only.login.management.system", loginBody.getUsername()));//对不起,你是管理员：{0}，只能登陆后台管理系统
            }

        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginBody.getUsername(), Constants.LOGIN_FAIL,
                        MessageUtils.message("user.password.not.match")));//用户不存在/密码错误
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(
                        AsyncFactory.recordLogininfor(loginBody.getUsername(), Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginBody.getUsername(), Constants.LOGIN_SUCCESS,
                MessageUtils.message("user.login.success")));//登录成功
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // 生成token
        System.out.println(loginUser);

        String token = tokenService.createToken(loginUser);
        // 登录IP和时间
        user.setLoginIp(loginUser.getIpaddr());
        user.setLoginDate(new Date(loginUser.getLoginTime()));
        userService.updateUser(user);

        return token;
    }

    /**
     * 发送 邮箱/手机 验证码
     *
     * @param captchaBody 注册数据
     * @return 生成uuid返回
     */
    @Async
    public String sendCaptchaCode(CaptchaBody captchaBody) {
        // 生成纯数字随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(6, VerifyCodeUtils.VERIFY_CODES_NUMBER);
        // 唯一标识
        String uuid = IdUtils.fastSimpleUUID();
        String verifyKey = Constants.CAPTCHA_EMAILORPHONE_CODE_KEY + ":" + uuid;

        String ipAddr = IpUtils.getIpAddr(ServletUtils.getRequest());//IP地址
        String email = captchaBody.getEmail();//邮箱
        String phonenumber = captchaBody.getPhonenumber();//手机号

        Integer num = redisCache.getCacheObject(Constants.CAPTCHA_SEND_EMAIL_LIMIT_KEY + ":" + ipAddr);
        if (StringUtils.isNotNull(num) && num <= 0) {
            log.error("ip: {} 2分钟之内已发送10次验证码", ipAddr);
            throw new BaseException(MessageUtils.message("user.jcaptcha.limit"));
        }

        if (StringUtils.isNull(num)) {
            num = 10;
        }

        if (Constants.EMAIL.equals(captchaBody.getCaptchaType())) {//邮箱验证码
            Object object = redisCache.getCacheObject(Constants.CAPTCHA_SEND_EMAIL_LIMIT_KEY + ":" + email);
            if (object != null) {
                throw new BaseException(MessageUtils.message("user.jcaptcha.limit"));
            }
            try {
                emailService.sendSimpleMail(emailService.getMailBeanBySendType(email, captchaBody.getType(), verifyCode));
                log.info("发送邮件成功，邮箱为：{},UUID为：{},邮箱验证码为：{}", email, uuid, verifyCode);
            } catch (Exception e) {
                log.info("发送邮件失败，邮箱为：{},UUID为：{},邮箱验证码为：{}", email, uuid, verifyCode);
                log.info("发送邮件失败-异常", e);
            }

            redisCache.setCacheObject(verifyKey, email + "|" + verifyCode + "|" + Constants.CAPTCHA_EXPIRATION_NUMBER, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);

            redisCache.setCacheObject(Constants.CAPTCHA_SEND_EMAIL_LIMIT_KEY + ":" + email, email, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);

        } else if (Constants.PHONE_NUMBER.equals(captchaBody.getCaptchaType())) {//短信验证码
            try {
                /** 这里需要调用异步发送手机验证码 **/
                log.info("异步发送手机短信接口未实现，手机号为：{},UUID为：{},手机验证码为：{}", phonenumber, uuid, verifyCode);
                /** 这里需要调用异步发送手机验证码 **/
            } catch (Exception e) {
                e.printStackTrace();
            }
            redisCache.setCacheObject(verifyKey, phonenumber + "|" + verifyCode + "|" + Constants.CAPTCHA_EXPIRATION_NUMBER,
                    Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        }
        redisCache.setCacheObject(Constants.CAPTCHA_SEND_EMAIL_LIMIT_KEY + ":" + ipAddr, (num - 1), Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        return uuid;
    }


    /**
     * @param retrievePasswordBody : 找回密码 实体
     * @Description: 用户找回密码
     * @Author:
     * @Date: 2021/1/11 15:50
     * @return: void
     **/
    public void retrievePassword(RetrievePasswordBody retrievePasswordBody) {
        SysUser user = null;

        // 验证 手机/邮箱 的验证码
        String verifyKey = Constants.CAPTCHA_EMAILORPHONE_CODE_KEY + ":" + retrievePasswordBody.getUuid();

        //获得验证码
        String value = redisCache.getCacheObject(verifyKey);

        if (StringUtils.isEmpty(value)) {
            throw new UserException("user.jcaptcha.expire", null);// 验证码过期
        }

        String emailOrPhone = value.split("\\|")[0];// 手机号或邮箱
        String code = value.split("\\|")[1];// 验证码
        int num = Integer.parseInt(value.split("\\|")[2]);// 已验证次数


        String captchaType = retrievePasswordBody.getCaptchaType();
        String email = retrievePasswordBody.getEmail();
        String phonenumber = retrievePasswordBody.getPhonenumber();


        if (Constants.EMAIL.equals(captchaType)) {
            if (!emailOrPhone.equals(email)) {
                throw new BaseException(MessageUtils.message("user.email.error"));//邮箱错误
            }
            user = userService.selectUserByUserName(email);
        } else if (Constants.PHONE_NUMBER.equals(captchaType)) {
            if (!emailOrPhone.equals(phonenumber)) {
                throw new BaseException(MessageUtils.message("user.mobile.phone.number.not.valid"));//手机号错误
            }
            user = userService.selectUserByUserName(phonenumber);
        } else {
            throw new BaseException(MessageUtils.message("user.no.retrievePassword.type"));//未指定找回方式
        }
        if (user == null) {
            throw new BaseException(MessageUtils.message("user.not.exists"));//找回密码失败,该账号不存在
        }

        if (num > 1) {
            redisCache.setCacheObject(verifyKey, emailOrPhone + "|" + code + "|" + (num - 1), Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        } else {
            redisCache.deleteObject(verifyKey);// 最后一次直接失效
        }

        if (!code.equals(retrievePasswordBody.getCode())) {// 判断验证码
            throw new UserException("user.jcaptcha.error", null);// 验证码错误
        }
        user.setPassword(SecurityUtils.encryptPassword(retrievePasswordBody.getNewPassword()));
        userService.updateUser(user);
        redisCache.deleteObject(verifyKey);// 成功直接失效
    }

}
