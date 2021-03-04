package com.qianxun.framework.security;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录对象
 * 
 */
public class LoginBody
{
    /**
     * 用户名
     */
    @NotBlank(message = "{user.name.is.empty}")//用户名不能为空
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(message = "{user.password.is.empty}")//密码不能为空
    private String password;

    /**
     * 验证码
     */
    private String code;

    /**
     * 唯一标识
     */
    private String uuid = "";

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }
}
