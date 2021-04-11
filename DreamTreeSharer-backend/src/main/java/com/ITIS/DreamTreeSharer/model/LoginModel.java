package com.ITIS.DreamTreeSharer.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @ClassName LoginModel
 * @Author LCX
 * @Date 2021 2021-03-26 8:36 p.m.
 * @Version 1.0
 * 登录实体类
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "LoginModel", description = "登录实体类")
public class LoginModel {
    @ApiModelProperty(value = "登录用户名", required = true)
    private String username;

    @ApiModelProperty(value = "登录密码", required = true)
    private String password;

    @ApiModelProperty(value = "登录验证码", required = true)
    private String captcha;
}
