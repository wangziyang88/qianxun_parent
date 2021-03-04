package com.qianxun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class QianXunApplication
{
    public static void main(String[] args)
    {
//        System.setProperty("spring.devtools.restart.enabled", "false");// 本地热部署开关
        SpringApplication.run(QianXunApplication.class, args);
        System.out.println(">>>>>> Hotal系统 启动成功 >>>>>>>>");
    }
}
