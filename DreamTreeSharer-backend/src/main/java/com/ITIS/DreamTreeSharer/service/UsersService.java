package com.ITIS.DreamTreeSharer.service;

import com.ITIS.DreamTreeSharer.entity.UsersEntity;
import com.ITIS.DreamTreeSharer.model.CRModel;
import com.ITIS.DreamTreeSharer.model.UsersModel;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author SummerLv
 * @since 2021-03-26
 */
public interface UsersService extends UserDetailsService{

    /**
     *
     * @param usersModel
     * @param request
     * @return
     * 管理员登录
     */
    CRModel login(UsersModel usersModel, HttpServletRequest request);

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    UsersEntity getCurrentUserInfo(String username);

    /**
     * 添加一个用户
     * @param usersModel
     * @return
     */
    CRModel addOneUser(UsersModel usersModel);

    /**
     * 发送短信验证码
     * @param phone
     * @return
     */
    CRModel getSmsCode(String phone);

    /**
     * 用户名是否重复
     * @param username
     * @return
     */
    CRModel usernameExisted(String username);
}
