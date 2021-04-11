package com.ITIS.DreamTreeSharer.controller;

import com.ITIS.DreamTreeSharer.config.common.Message;
import com.ITIS.DreamTreeSharer.model.CRModel;
import com.ITIS.DreamTreeSharer.model.LoginModel;
import com.ITIS.DreamTreeSharer.model.UserModel;
import com.ITIS.DreamTreeSharer.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @ClassName LoginController
 * @Author LCX
 * @Date 2021 2021-03-26 9:22 p.m.
 * @Version 1.0
 **/
@RestController
//@RequestMapping("/login")
@Api(tags = "LoginController")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "登录后返回 token")
    @PostMapping("/login")
    public CRModel login(@RequestBody LoginModel loginModel, HttpServletRequest request) {
        return loginService.login(loginModel, request);
    }


    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping("/get-current-user-info")
    public UserModel getCurrentUserInfo(Principal principal) {
       if (null == principal) {
           return null;
       }
        String username = principal.getName();
        UserModel userModel = loginService.getCurrentUserInfo(username);
        //不能返回用户密码
        userModel.getCurrentUserInfo().setUserPassword(null);
        return userModel;
    }


    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public CRModel logout() {
        return CRModel.success(Message.SUC_LOGOUT);
    }

}
