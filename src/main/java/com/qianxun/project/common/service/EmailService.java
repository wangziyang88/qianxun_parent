package com.qianxun.project.common.service;

import com.qianxun.common.constant.UserConstants;
import com.qianxun.common.exception.BaseException;
import com.qianxun.common.exception.user.ParameterException;
import com.qianxun.common.utils.MessageUtils;
import com.qianxun.project.system.domain.SysUser;
import com.qianxun.project.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;


/**
 * @Author:
 * @Email:
 * @Description: 发送邮箱
 * @Date:2021/1/13 15:17
 * @Version:1.0
 */
@Service
public class EmailService {
    protected final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSenderImpl mailSender;


    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    @Qualifier("threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private ISysUserService userService;


    /**
     * @param mailBean: 邮件实体
     * @Description: 发送简单的邮件
     * @Author:
     * @Date: 2021/1/13 15:38
     * @return: void
     **/
    public void sendSimpleMail(MailBean mailBean) {
        taskExecutor.execute(() -> {
            String recName = mailBean.getRecName();
            String topic = mailBean.getTopic();
            String content = mailBean.getContent();
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(username);
                message.setTo(recName);
                message.setSubject(topic);
                message.setText(content);
                mailSender.send(message);
            } catch (Exception e) {
                log.error("邮件发送失败{}", e.getMessage());
            }
        });
    }

    /**获得 发送的MailBean*/
    public MailBean getMailBeanBySendType(String email, Integer type, String verifyCode) {
        MailBean mailBean = null;
        SysUser user = new SysUser();
        user.setEmail(email);
        if (type == 1) {//注册
            mailBean = new MailBean(email, MessageUtils.message("email.register.topic"), MessageUtils.message("email.register.content", verifyCode));
            if (UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
                throw new BaseException(MessageUtils.message("user.is.exists")); //注册账户已存在
            }
        } else if (type == 2) {//找回密码
            mailBean = new MailBean(email, MessageUtils.message("email.earlyPassword.topic"), MessageUtils.message("email.earlyPassword.content", verifyCode));
            if (UserConstants.UNIQUE.equals(userService.checkEmailUnique(user))) {
                throw new BaseException(MessageUtils.message("user.not.exists"));//该账号不存在
            }
        } else {
            log.error("发送验证码参数参数错误");
            throw new ParameterException();
        }
        return mailBean;
    }


}


