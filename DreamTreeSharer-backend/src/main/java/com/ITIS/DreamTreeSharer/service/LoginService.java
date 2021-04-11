package com.ITIS.DreamTreeSharer.service;

import com.ITIS.DreamTreeSharer.model.CRModel;
import com.ITIS.DreamTreeSharer.model.LoginModel;
import com.ITIS.DreamTreeSharer.model.UserModel;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName LoginService
 * @Author LCX
 * @Date 2021 2021-03-26 9:30 p.m.
 * @Version 1.0
 **/
public interface LoginService {
    /**
     *
     * @param loginModel
     * @param request
     * @return
     * 管理员登录
     */
    CRModel login(LoginModel loginModel, HttpServletRequest request);

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    UserModel getCurrentUserInfo(String username);
}
