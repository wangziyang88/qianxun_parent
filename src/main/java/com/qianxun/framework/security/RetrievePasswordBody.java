package com.qianxun.framework.security;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @Author:
 * @Email:
 * @Description: 找回密码对象
 * @Date:2021/1/11 15:33
 * @Version:1.0
 */
public class RetrievePasswordBody{

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


    /**
     * 手机/邮箱的验证码
     */
    private String code;

    /** 新密码**/
    @NotBlank(message = "user.newPassword.is.empty")//改的密码不能为空
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[\\da-zA-Z.~!@#$%^&*]{8,}$", message = "{user.password.format.error}")
    private String newPassword;

    /**
     * 验证码的UUID
     */
    private String uuid;



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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "RetrievePasswordBody{" +
                "captchaType='" + captchaType + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", email='" + email + '\'' +
                ", code='" + code + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
