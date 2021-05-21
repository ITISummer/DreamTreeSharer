package com.ITIS.DreamTreeSharer.config.constants;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName Message
 * @Author LCX
 * @Date 2021 2021-03-26 6:51 p.m.
 * @Version 1.0
 * 后端返回的信息提示
 **/
@ApiModel(value = "Message", description = "系统提示信息")
public class Message {
    /*----------------------------成功消息---------------------------*/
    @ApiModelProperty(value = "处理成功！")
    public static final String SUCCESS = "成功！";
    @ApiModelProperty(value = "注销成功！")
    public static final String SUC_LOGOUT = "注销成功！";
    /*----------------------------警告消息---------------------------*/
    @ApiModelProperty(value = "处理失败！")
    public static final String WARNING = "失败！";
    @ApiModelProperty(value = "用户名或密码不正确！")
    public static final String WAR_LOGIN_INVALID_USERNAME_PASSWORD = "用户名或密码不正确！";
    @ApiModelProperty(value = "账户被禁用，请联系管理员！")
    public static final String WAR_IS_FORBIDDEN = "账户被禁用，请联系管理员！";
    public static final String WAR_NO_PERMISSION = "权限不够！";
    public static final String WAR_NOT_LOGIN = "尚未登陆，请先登录~~~";
    public static final String WAR_CAPTCHA = "验证码输入错误！";
    public static final String WAR_WRONG_KEY = "错误的文件名！";
    /*----------------------------错误消息---------------------------*/
    public static final String ERROR = "错误！";
    public static final String ERR_SERVER_INTERNAL = "服务器内部错误！";

    public static final String SUC_SEND_SMS = "短信验证码发送成功！";
    public static final String SUC_REGISTER = "注册成功！";
    public static final String WAR_USERNAME_EXISTED = "用户名已存在！";
    public static final String SUC_USERNAME = "恭喜你！用户名可以使用！";
    public static final String WAR_NOT_FOUND_USER = "该用户不存在！";
    public static final String WAR_CODE_IN_VALIDITY = "邮件五分钟之内有效！请不要频繁发送！";
    public static final String SUC_SEND_EMAIL_CODE = "邮件发送成功！请注意查收验证码！";
}
