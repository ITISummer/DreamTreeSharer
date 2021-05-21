package com.ITIS.DreamTreeSharer.config.constants;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName StatusCode
 * @Author LCX
 * @Date 2021 2021-03-26 6:49 p.m.
 * @Version 1.0
 * 后端返回的状态码
 * [200,300) - 成功状态码
 * [400,500) - 提示信息
 * [500,600) - 错误状态码
 **/
@ApiModel(value = "StatusCode", description = "系统状态码")
public class StatusCode {
    /*----------------------------成功状态码---------------------------*/
    @ApiModelProperty(value = "200-成功")
    public static final Integer SUCCESS = 200;
    /*----------------------------警告状态码---------------------------*/
    @ApiModelProperty(value = "400-提示失败")
    public static final Integer WARNING = 400;
    @ApiModelProperty(value = "401-用户未登录")
    public static final Integer WAR_USER_NOT_LOGIN = 401;
    @ApiModelProperty(value = "403-用户权限不足")
    public static final Integer WAR_USER_NO_PERMISSION = 403;
    /*----------------------------错误状态码---------------------------*/
    @ApiModelProperty(value = "500-服务器内部错误")
    public static final Integer ERROR = 500;

}
