package com.ITIS.DreamTreeSharer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Collection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author SummerLv
 * @since 2021-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("admins")
@ApiModel(value="AdminsEntity对象", description="管理员表")
public class AdminsEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "管理员id")
    @TableId
    private String adminId;
    @ApiModelProperty(value = "管理员名")
    private String adminName;
    @ApiModelProperty(value = "管理员登录密码")
    private String adminPassword;
    @ApiModelProperty(value = "创建管理员时间")
    private LocalDateTime adminCreateTime;
    @ApiModelProperty(value = "管理员是否被启用")
    private Boolean adminEnabled;
    @ApiModelProperty(value = "管理员最新登录时间")
    private LocalDateTime adminLoginTime;


}
