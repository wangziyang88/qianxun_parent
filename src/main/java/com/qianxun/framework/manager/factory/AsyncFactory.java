package com.qianxun.framework.manager.factory;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qianxun.common.constant.Constants;
import com.qianxun.common.utils.AddressUtils;
import com.qianxun.common.utils.IpUtils;
import com.qianxun.common.utils.LogUtils;
import com.qianxun.common.utils.ServletUtils;
import com.qianxun.common.utils.SpringUtils;
import com.qianxun.project.monitor.domain.SysLogininfor;
import com.qianxun.project.monitor.domain.SysOperLog;
import com.qianxun.project.monitor.service.ISysLogininforService;
import com.qianxun.project.monitor.service.ISysOperLogService;

import eu.bitwalker.useragentutils.UserAgent;

/**
 * 异步工厂（产生任务用）
 * 
 */
public class AsyncFactory
{
    private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");

    /**
     * 记录登录信息
     */
    public static TimerTask recordLogininfor(final String username, final String status, final String message,
            final Object... args)
    {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        return new TimerTask()
        {
            @Override
            public void run()
            {
                String address = AddressUtils.getRealAddressByIP(ip);
                StringBuilder s = new StringBuilder();
                s.append(LogUtils.getBlock(ip));
                s.append(address);
                s.append(LogUtils.getBlock(username));
                s.append(LogUtils.getBlock(status));
                s.append(LogUtils.getBlock(message));
                sys_user_logger.info(s.toString(), args);
                String os = userAgent.getOperatingSystem().getName();
                String browser = userAgent.getBrowser().getName();
                SysLogininfor logininfor = new SysLogininfor();
                logininfor.setUserName(username);
                logininfor.setIpaddr(ip);
                logininfor.setLoginLocation(address);
                logininfor.setBrowser(browser);
                logininfor.setOs(os);
                logininfor.setMsg(message);
                if (Constants.LOGIN_SUCCESS.equals(status) || Constants.LOGOUT.equals(status))
                {
                    logininfor.setStatus(Constants.SUCCESS);
                }
                else if (Constants.LOGIN_FAIL.equals(status))
                {
                    logininfor.setStatus(Constants.FAIL);
                }
                // 插入数据
                SpringUtils.getBean(ISysLogininforService.class).insertLogininfor(logininfor);
            }
        };
    }

    /**
     * 操作日志记录
     */
    public static TimerTask recordOper(final SysOperLog operLog)
    {
        return new TimerTask()
        {
            @Override
            public void run()
            {
                operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
                SpringUtils.getBean(ISysOperLogService.class).insertOperlog(operLog);
            }
        };
    }
}
