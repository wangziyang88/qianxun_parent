package com.qianxun.framework.security;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @Author:
 * @Email:
 * @Description:
 * @Date:2021/3/1 11:29
 * @Version:1.0
 */
public class SecurityPwdBodyVo {


    /** 旧安全密码**/
    @NotBlank(message = "{user.password.is.empty}")//安全密码不能为空
    private String oldSecurityPwd;

    /** 新安全密码**/
    @NotBlank(message = "{user.newPassword.is.empty}")//改的安全密码不能为空
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[\\da-zA-Z.~!@#$%^&*]{8,}$", message = "{user.password.format.error}")
    private String newSecurityPwd;

    public String getOldSecurityPwd() {
        return oldSecurityPwd;
    }

    public void setOldSecurityPwd(String oldSecurityPwd) {
        this.oldSecurityPwd = oldSecurityPwd;
    }

    public String getNewSecurityPwd() {
        return newSecurityPwd;
    }

    public void setNewSecurityPwd(String newSecurityPwd) {
        this.newSecurityPwd = newSecurityPwd;
    }

    @Override
    public String toString() {
        return "securityPwdBodyVo{" +
                "oldSecurityPwd='" + oldSecurityPwd + '\'' +
                ", newSecurityPwd='" + newSecurityPwd + '\'' +
                '}';
    }
}
