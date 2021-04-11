package com.ITIS.DreamTreeSharer.model;

import com.ITIS.DreamTreeSharer.entity.UsersEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * @ClassName UserModel
 * @Author LCX
 * @Date 2021 2021-03-26 9:45 p.m.
 * @Version 1.0
 * 用户实体类 security 验证需要使用到
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "UserModel", description = "用户实体类-包含(user,admin)")
public class UserModel implements Serializable, UserDetails {
    private static final long serialVersionUID = 1L;

    //当前登录用户
    private transient UsersEntity currentUserInfo;

    public UserModel(UsersEntity user) {
        if (user != null) {
            this.currentUserInfo = user;
        }
    }

    @ApiModelProperty(value = "jwt token")
    private String jwtToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return currentUserInfo.getUserPassword();
    }

    @Override
    public String getUsername() {
        return currentUserInfo.getUserUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return currentUserInfo.getUserEnabled();
    }
}
