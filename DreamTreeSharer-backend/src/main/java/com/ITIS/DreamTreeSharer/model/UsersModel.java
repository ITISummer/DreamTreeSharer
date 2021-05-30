package com.ITIS.DreamTreeSharer.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName UsersModel
 * @Author LCX
 * @Date 2021 2021-03-26 9:45 p.m.
 * @Version 1.0
 * 用户实体类 security 验证需要使用到
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "UsersModel", description = "用户实体类-包含(user,admin)")
public class UsersModel implements Serializable{
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "jwt token")
    private String jwtToken;
//    公共字段
    @ApiModelProperty(value = "登录用户名", required = true)
    private String username;
    @ApiModelProperty(value = "登录密码", required = true)
    private String password;
    @ApiModelProperty(value = "登录验证码")
//    用户登录所需字段
    private String captcha;
//    用户注册所需字段
    private String phone;
    private String smsCode;
}
