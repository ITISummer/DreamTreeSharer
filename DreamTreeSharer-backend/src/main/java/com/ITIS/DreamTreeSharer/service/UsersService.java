package com.ITIS.DreamTreeSharer.service;

import com.ITIS.DreamTreeSharer.entity.UsersEntity;
import com.ITIS.DreamTreeSharer.model.CRModel;
import com.ITIS.DreamTreeSharer.model.UsersModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author SummerLv
 * @since 2021-03-26
 */
public interface UsersService extends UserDetailsService {

    /**
     * @return 用户登录
     */
    CRModel login(UsersModel usersModel, HttpServletRequest request);

    /**
     * 根据用户名获取用户
     */
    UsersEntity getCurrentUserInfo(String username);

    /**
     * 添加一个用户
     */
    CRModel addOneUser(UsersModel usersModel);

    /**
     * 发送短信验证码
     */
    CRModel getSmsCode(String phone);

    /**
     * 用户名是否重复
     */
    CRModel usernameExisted(String username);

    /**
     * 根据关键词获取用户列表
     */
    CRModel getUserList(String keywords);

    /**
     * 更新用户邮箱或者手机号
     */
    CRModel updateEmailOrMobile(String flag, String emailOrMobile, String code);

    /**
     * 更新用户邮箱或者手机号之前获取验证码
     */
    CRModel getCode(String flag, String emailOrMobile);

    /**
     * 更新用户密码
     */
    CRModel updatePwd(String pwd);

    /**
     * 更新用户头像
     */
    CRModel updateAvatar(String newAvatarUrl);

    /**
     * 前台模糊查询
     */
    CRModel fuzzySearch(String flag, String search, int limit, int offset);

}
