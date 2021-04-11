package com.ITIS.DreamTreeSharer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author SummerLv
 * @since 2021-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("users")
@ApiModel(value="UsersEntity对象", description="用户表")
public class UsersEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户名")
    private String userUsername;

    @ApiModelProperty(value = "用户登录密码")
    private String userPassword;

    @ApiModelProperty(value = "用户性别")
    private String userSex;

    @ApiModelProperty(value = "用户生日")
    private LocalDate userBirthday;

    @ApiModelProperty(value = "用户自定义描述")
    private String userDescription;

    @ApiModelProperty(value = "用户头像地址")
    private String userAvatarUrl;

    @ApiModelProperty(value = "用户手机号")
    private String userPhone;

    @ApiModelProperty(value = "用户邮箱")
    private String userEmail;

    @ApiModelProperty(value = "创建用户时间")
    private LocalDateTime userCreateTime;

    @ApiModelProperty(value = "用户最新登录时间")
    private LocalDateTime userLoginTime;

    @ApiModelProperty(value = "用户是否被禁用 1-表示被禁用 0-表示没被禁用")
    private Boolean userEnabled;


}
