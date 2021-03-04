package com.qianxun.framework.security.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.qianxun.common.constant.Constants;
import com.qianxun.common.exception.BaseException;
import com.qianxun.common.exception.CustomException;
import com.qianxun.common.exception.user.CaptchaException;
import com.qianxun.common.exception.user.CaptchaExpireException;
import com.qianxun.common.exception.user.UserPasswordNotMatchException;
import com.qianxun.common.utils.MessageUtils;
import com.qianxun.framework.manager.AsyncManager;
import com.qianxun.framework.manager.factory.AsyncFactory;
import com.qianxun.framework.redis.RedisCache;
import com.qianxun.framework.security.LoginUser;
import com.qianxun.project.system.domain.SysUser;
import com.qianxun.project.system.service.ISysUserService;

/**
 * 登录校验方法
 * 
 */
@Component
public class SysLoginService
{
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;
    
    protected final Logger log = LoggerFactory.getLogger(SysLoginService.class);
    
    @Autowired
    private ISysUserService userService;

    /**
     * 登录验证
     * 
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid)
    {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
        // 用户验证
        Authentication authentication = null;
        try
        {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
            
            // 判断用户类型
            SysUser user = userService.selectUserByUserName(username);
            if ("00".equals(user.getUserType())==false) {
                log.info("登录用户：{} 非管理员，不可登录后台管理系统！！！", username);
                throw new BaseException( MessageUtils.message("user.area.no.administrator")+ username);//对不起，您不是管理员
            }
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));//用户不存在/密码错误
                throw new UserPasswordNotMatchException();
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));//
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        return tokenService.createToken(loginUser);
    }
}
