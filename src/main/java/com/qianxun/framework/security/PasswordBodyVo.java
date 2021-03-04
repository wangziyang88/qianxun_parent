package com.qianxun.framework.security;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 修改密码
 */
public class PasswordBodyVo {
    
    /** 旧密码**/
    @NotBlank(message = "{user.password.is.empty}")//密码不能为空
    private String oldPassword;
    
    /** 新密码**/
    @NotBlank(message = "{user.newPassword.is.empty}")//改的密码不能为空
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[\\da-zA-Z.~!@#$%^&*]{8,}$", message = "{user.password.format.error}")
    private String newPassword;

    /** 安全密码**/
    @NotBlank()//安全密码不能为空
    private String securityPassword;

    
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getSecurityPassword() {
        return securityPassword;
    }

    public void setSecurityPassword(String securityPassword) {
        this.securityPassword = securityPassword;
    }
}
