package com.ITIS.DreamTreeSharer.controller;

import com.ITIS.DreamTreeSharer.model.CRModel;
import com.ITIS.DreamTreeSharer.service.UsersService;
import com.ITIS.DreamTreeSharer.utils.RedisUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @ClassName CaptchaController
 * @Author LCX
 * @Date 2021 2021-03-31 1:35 p.m.
 * @Version 1.0
 * 获得 captcha 存入 session 中
 * [SpringBoot中设置不同的“session”（缓存）过期时间](https://codeleading.com/article/72382055716/)
 **/
@RestController
@Api(tags = "CaptchaController")
public class CaptchaController {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    private DefaultKaptcha defaultKaptcha;
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UsersService usersService;

    @ApiOperation(value = "验证码")
    @GetMapping(value = "/captcha", produces = "image/jpeg")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        //定义response输出类型为image/jpeg类型
        response.setDateHeader("Expires", 0);
        //Set standard Http/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        //Set IE extended Http/1.1 no-cache headers(use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        //Set standard Http/1.0 no-cache header
        response.setHeader("Pragma", "no-cache");
        //return a jpeg
        response.setContentType("image/jpeg");

        //获取验证码文本内容
        String text = defaultKaptcha.createText();
        logger.info("验证码:" + text);
//        将验证码放入到session/redis中
        request.getSession().setAttribute("captcha", text);

        // 将验证码放入到 redis 中设置60s过期
//        redisUtil.setKey(request.getSession().getId(), text, 60);
        //根据验证码文本生成验证码图片
        BufferedImage image = defaultKaptcha.createImage(text);
        //获取响应输出流，输出给用户
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            //输出流输出图片
            ImageIO.write(image, "jpg", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @ApiOperation(value = "更新用户邮箱或者手机号前获取验证码")
    @GetMapping("/get-code/{flag}/{emailOrMobile}")
    public CRModel getCode(@PathVariable("flag") String flag, @PathVariable("emailOrMobile") String emailOrMobile) {
        return usersService.getCode(flag, emailOrMobile);
    }


    @ApiOperation(value = "获取短信验证码")
    @GetMapping("/get-sms-code/{phone}")
    public CRModel getSmsCode(@PathVariable("phone") String phone) {
        return usersService.getSmsCode(phone);
    }


}

