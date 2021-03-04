package com.qianxun.common.constant;

import io.jsonwebtoken.Claims;


public class Constants
{
    
    public static final String UTF8 = "UTF-8";

    
    public static final String GBK = "GBK";

    
    public static final String HTTP = "http://";

    
    public static final String HTTPS = "https://";

    
    public static final String SUCCESS = "0";

    
    public static final String FAIL = "1";

    
    public static final String LOGIN_SUCCESS = "Success";

    
    public static final String LOGOUT = "Logout";

    
    public static final String LOGIN_FAIL = "Error";

    
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";
    
    
    public static final String CAPTCHA_EMAILORPHONE_CODE_KEY = "captcha_emailorphone_codes:";

    public static final String CAPTCHA_SEND_EMAIL_LIMIT_KEY = "captcha_limit_codes:";//限制发送验证码

    public static final String OTC_GC_ORDER_KEY="otc_gc_order:";//gc订单缓存


    // 验证码有效期内可错误(次)
    public static final Integer CAPTCHA_EXPIRATION_NUMBER = 5;

    
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";
    
    
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    
    public static final Integer CAPTCHA_EXPIRATION = 2;

    
    public static final String TOKEN = "token";

    
    public static final String TOKEN_PREFIX = "Bearer ";

    
    public static final String LOGIN_USER_KEY = "login_user_key";

    public static final String EMAIL = "email";

    public static final String PHONE_NUMBER = "phonenumber";


    public static final String JWT_USERID = "userid";

    
    public static final String JWT_USERNAME = Claims.SUBJECT;

    
    public static final String JWT_AVATAR = "avatar";

    
    public static final String JWT_CREATED = "created";

    
    public static final String JWT_AUTHORITIES = "authorities";

    
    public static final String SYS_CONFIG_KEY = "sys_config:";

    
    public static final String SYS_DICT_KEY = "sys_dict:";

    
    public static final String RESOURCE_PREFIX = "/profile";

}
