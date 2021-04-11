package com.ITIS.DreamTreeSharer.config.common;

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
    /*----------------------------错误消息---------------------------*/
    @ApiModelProperty(value = "处理失败")
    public static final String ERROR = "失败！";
    @ApiModelProperty(value = "用户名或密码不正确！")
    public static final String ERR_LOGIN_INVALID_USERNAME_PASSWORD = "用户名或密码不正确！";
    @ApiModelProperty(value = "账户被禁用，请联系管理员！")
    public static final String ERR_IS_FORBIDDEN = "账户被禁用，请联系管理员！";
    public static final String ERR_NO_PERMISSION = "权限不够！";
    public static final String ERR_NOT_LOGIN = "尚未登陆，请先登录！";
    public static final String ERR_CAPTCHA = "验证码输入错误！";
}
