package com.ITIS.DreamTreeSharer.service.impl;

import com.ITIS.DreamTreeSharer.config.common.Message;
import com.ITIS.DreamTreeSharer.config.common.StatusCode;
import com.ITIS.DreamTreeSharer.config.security.JwtTokenUtil;
import com.ITIS.DreamTreeSharer.dao.LoginDao;
import com.ITIS.DreamTreeSharer.entity.UsersEntity;
import com.ITIS.DreamTreeSharer.model.CRModel;
import com.ITIS.DreamTreeSharer.model.LoginModel;
import com.ITIS.DreamTreeSharer.model.UserModel;
import com.ITIS.DreamTreeSharer.service.LoginService;
import com.ITIS.DreamTreeSharer.utils.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.org.apache.bcel.internal.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName LoginServiceImpl
 * @Author LCX
 * @Date 2021 2021-03-26 9:30 p.m.
 * @Version 1.0
 **/
@Service
public class LoginServiceImpl implements LoginService,UserDetailsService{
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private LoginDao loginDao;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    String tokenHead;


    /**
     * 登陆后返回 token
     * @param loginModel
     * @param request
     * @return
     *
     * 前端输入用户名和密码，后台查数据库判断用户名和密码是否正确，
     * 如果正确则生成 jwt token 返回给前端，后面的每一次请求都会携带此 jwt token，
     * 在每一次前端发起请求前都会拦截请求进行 token 是否有效的验证；
     * 否则提示继续输入。
     */
    @Override
    public CRModel login(LoginModel loginModel, HttpServletRequest request) {
        //判断验证码是否正确
        String captchaCode = (String)request.getSession().getAttribute("captcha");
        if (!captchaCode.equalsIgnoreCase(loginModel.getCaptcha())) { //忽略大小写
            return CRModel.error(Message.ERR_CAPTCHA);
        }
        //获取登录信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginModel.getUsername());
//        if (null == userDetails || !passwordEncoder.matches(password, userDetails.getPassword())) {
            if (null == userDetails || !userDetails.getPassword().equals(MD5.md5Encrypt(loginModel.getPassword()))) {
            return CRModel.error(Message.ERR_LOGIN_INVALID_USERNAME_PASSWORD);
        }
        if (!userDetails.isEnabled()) {
            return CRModel.error(Message.ERR_IS_FORBIDDEN);
        }
        //更新 security 登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //生成 token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CRModel.success(Message.SUCCESS, tokenMap);
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    @Override
    public UserModel getCurrentUserInfo(String username) {
        //TODO 判断如果是 admin 用户则向 admins 发查询请求；如果是 user 用户，则向 users 发查询请求
        //TODO 两种方法：通过
        return new UserModel(loginDao.selectOne(new QueryWrapper<UsersEntity>().eq("user_username",username).eq("user_enabled",true)));
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getCurrentUserInfo(username);
    }
}
