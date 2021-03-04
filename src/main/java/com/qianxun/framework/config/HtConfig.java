package com.qianxun.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "qx")
public class HtConfig
{
    
    private String name;

    
    private static String profile;

    
    private static boolean addressEnabled;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public static String getProfile()
    {
        return profile;
    }

    public void setProfile(String profile)
    {
        HtConfig.profile = profile;
    }

    public static boolean isAddressEnabled()
    {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled)
    {
        HtConfig.addressEnabled = addressEnabled;
    }

    
    public static String getAvatarPath()
    {
        return getProfile() + "/avatar";
    }

    
    public static String getDownloadPath()
    {
        return getProfile() + "/download/";
    }

    
    public static String getUploadPath()
    {
        return getProfile() + "/upload";
    }
}