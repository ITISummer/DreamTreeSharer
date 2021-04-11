package com.ITIS.DreamTreeSharer.config.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName StatusCode
 * @Author LCX
 * @Date 2021 2021-03-26 6:49 p.m.
 * @Version 1.0
 * 后端返回的状态码
 **/
@ApiModel(value = "StatusCode", description = "系统状态码")
public class StatusCode {
    /*----------------------------成功状态码---------------------------*/
    /**
     * 表示处理成功
     */
    @ApiModelProperty(value = "200-成功")
    public static final Integer SUCCESS = 200;
    /*----------------------------错误状态码---------------------------*/
    /**
     * 提示错误
     */
    @ApiModelProperty(value = "400-提示错误")
    public static final Integer ERROR = 400;
    /**
     * 用户未登录
     */
    @ApiModelProperty(value = "401-用户未登录")
    public static final Integer WARN = 401;
    /**
     * 用户权限不足
     */
    @ApiModelProperty(value = "403-用户权限不足")
    public static final Integer PERMISSION = 403;
}
