package com.qianxun.framework.security;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 手机邮箱 效验体
 */
public class CaptchaBody {
    
    /** 验证码类型（email=邮箱  phonenumber=手机号）*/
    @NotBlank(message = "{user.no.registration.type}")//注册类型不能为空
    private String captchaType;
    
    /** 手机号**/
    private String phonenumber;
    
    /** 邮箱号**/
    private String email;

    /** 验证码用于类型  1：注册 2：找回密码*/
    @NotNull(message ="{user.verif.code.not.empty}")//验证码用于类型不能为空
    private Integer type;
    
    public String getCaptchaType() {
        return captchaType;
    }

    public void setCaptchaType(String captchaType) {
        this.captchaType = captchaType;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
