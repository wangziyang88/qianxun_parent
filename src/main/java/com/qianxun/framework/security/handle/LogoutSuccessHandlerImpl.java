package com.qianxun.framework.security.handle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.alibaba.fastjson.JSON;
import com.qianxun.common.constant.Constants;
import com.qianxun.common.constant.HttpStatus;
import com.qianxun.common.utils.ServletUtils;
import com.qianxun.common.utils.StringUtils;
import com.qianxun.framework.manager.AsyncManager;
import com.qianxun.framework.manager.factory.AsyncFactory;
import com.qianxun.framework.security.LoginUser;
import com.qianxun.framework.security.service.TokenService;
import com.qianxun.framework.web.domain.AjaxResult;

@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler
{
    @Autowired
    private TokenService tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser))
        {
            String userName = loginUser.getUsername();
            tokenService.delLoginUser(loginUser.getToken());
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGOUT, "退出成功"));
        }
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(HttpStatus.SUCCESS, "退出成功")));
    }
}
