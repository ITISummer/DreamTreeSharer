package com.ITIS.DreamTreeSharer.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @ClassName EmailUtil
 * @Author LCX
 * @Date 2021 2021-05-07 10:02 a.m.
 * @Version 1.0
 * [SpringDataRedis 项目搭建](https://www.bilibili.com/video/BV1tZ4y1P7dT?p=197&spm_id_from=pageDriver)
 * [SpringBoot 发送邮件和附件（实用版）](https://www.jianshu.com/p/5eb000544dd7)
 **/
@Component
public class EmailUtil {

    @Value("${spring.mail.username}")
    private String from;
    /**
     * 发送邮件的 api
     */
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private RedisUtil redisUtil;
    /**
     * 发送邮件
     * @param email
     * @param code
     * @return
     */
    public boolean sendEmailCode(String email, String code) {
        // 发送邮件前检验redis中是否存在键值对
        if (redisUtil.getValue(email) != null) {
            return false;
        } else {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setFrom(from);
        emailMessage.setTo(email);
        emailMessage.setSubject("【ITIS】更新邮箱");
        emailMessage.setText("您正在申请更新邮箱，请尽快输入以下验证码："+code+" 进行更新邮箱验证！该验证码五分钟内有效！");
//        String token = GenerateTokenUtil.getToken(email);
        redisUtil.setKey(email,code);
        javaMailSender.send(emailMessage);
        }
        return true;
    }
}
