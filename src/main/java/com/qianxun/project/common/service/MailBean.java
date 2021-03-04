package com.qianxun.project.common.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author:
 * @Email:
 * @Description: 邮件信息
 * @Date:2021/1/13 17:28
 * @Version:1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailBean{
    /**
     * 发送地址
     */
    private String recName;

    /**
     * 发送主题
     */
    private String topic;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 附件列表
     */

    private List<String> attachments;

    public MailBean(String recName, String topic, String content) {
        this.recName = recName;
        this.topic = topic;
        this.content = content;
    }
}
