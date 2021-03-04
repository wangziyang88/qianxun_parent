package com.qianxun.framework.web.controller;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

import com.qianxun.common.constant.Constants;
import com.qianxun.common.core.text.Convert;
import com.qianxun.common.exception.user.ParameterException;
import com.qianxun.common.exception.user.UserException;
import com.qianxun.common.utils.*;
import com.qianxun.framework.security.LoginUser;
import com.qianxun.project.system.domain.SysUser;
import com.qianxun.project.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qianxun.common.constant.HttpStatus;
import com.qianxun.framework.web.domain.AjaxResult;
import com.qianxun.framework.web.page.PageDomain;
import com.qianxun.framework.web.page.TableDataInfo;
import com.qianxun.framework.web.page.TableSupport;

/**
 * web层通用数据处理
 *
 */
public class BaseController
{
    protected final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private ISysUserService userService;

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected TableDataInfo getDataTable(List<?> list)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * 响应返回结果
     * 
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows)
    {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 获取用户ID
     */
    protected long getUserId()
    {
        long userId = Convert.toLong(SecurityUtils.getLoginUser().getUser().getUserId(), 0L);
        return userId;
    }

    /**
     * 获取登录用户信息
     */
    protected LoginUser getLoginUser()
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        return loginUser;
    }

    /**
     * @Description: 验证格式
     **/
    protected void checkFormat(String captchaType, String email, String phoneNum){
        if (Constants.EMAIL.equals(captchaType)) {
            if (!StringUtils.isEmail(email)) {
                throw new UserException("user.email.not.valid",null);// 邮箱格式错误
            }
        }else if (Constants.PHONE_NUMBER.equals(captchaType)) {
            if (!StringUtils.isPhoneNumber(phoneNum)) {
                throw new UserException("user.mobile.phone.number.not.valid",null);// 手机号格式错误
            }
        }else {
            throw new ParameterException();// 参数错误
        }
    }


    /**
     * @Description: 判断安全密码是否正确
     **/
    protected void checkSecurityPassword(String securityPassword){
        String securityPassword1 = getLoginUser().getUser().getSecurityPassword();

        if (StringUtils.isEmpty(securityPassword)||!securityPassword1.equals(securityPassword)){
            throw new UserException("user.security.password.error",null);// 安全密码错误
        }
    }

}
