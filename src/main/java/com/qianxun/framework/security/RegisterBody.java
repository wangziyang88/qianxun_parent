package com.qianxun.framework.security;

import com.qianxun.framework.aspectj.lang.annotation.Excel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 用户注册对象
 * 
 * @author live
 */
public class RegisterBody
{
    /** 注册类型（email=邮箱  phonenumber=手机号）**/
    @NotBlank(message = "{user.no.registration.type}")//注册类型不能为空
    private String captchaType;
    
    /**
     * 手机号
     */
    private String phonenumber;

    /**
     * 邮箱
     */
    private String email;


    /** 用户账号 */
    @NotBlank(message = "{user.name.is.empty}")
    @Size(min = 0, max = 30, message = "{user.name.too.long}")
    private String userName;


    /**
     * 用户密码
     */
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[\\da-zA-Z.~!@#$%^&*]{8,}$", message = "{user.password.format.error}")
    @NotBlank(message = "{user.password.is.empty}")//密码不能为空
    private String password;



    /** 安全密码 */
    @Pattern(regexp = "^\\d{6}$", message = "{user.securityPassword.is.empty}")
    private String securityPassword;

    
    /**
     * 验证码的UUID
     */
    private String uuid;
    
    /**
     * 手机/邮箱的验证码
     */
    @NotBlank(message = "user.jcaptcha.is.empty")//验证码不能为空
    private String code;
    
    /**
     * 邀请码
     */
    private String inviteCode;

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



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getSecurityPassword() {
        return securityPassword;
    }

    public void setSecurityPassword(String securityPassword) {
        this.securityPassword = securityPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "RegisterBody{" +
                "captchaType='" + captchaType + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", securityPassword='" + securityPassword + '\'' +
                ", uuid='" + uuid + '\'' +
                ", code='" + code + '\'' +
                ", inviteCode='" + inviteCode + '\'' +
                '}';
    }
}
