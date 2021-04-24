package com.ITIS.DreamTreeSharer.controller;


import com.ITIS.DreamTreeSharer.config.common.Message;
import com.ITIS.DreamTreeSharer.config.common.StatusCode;
import com.ITIS.DreamTreeSharer.entity.UsersEntity;
import com.ITIS.DreamTreeSharer.model.CRModel;
import com.ITIS.DreamTreeSharer.model.UsersModel;
import com.ITIS.DreamTreeSharer.service.UsersService;
import com.ITIS.DreamTreeSharer.utils.QiniuToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.UUID;

/**
 * @ClassName UsersController
 * @Author LCX
 * @Date 2021 2021-03-26 9:22 p.m.
 * @Version 1.0
 **/
@RestController
@Api(tags = "UsersController")
//@RequestMapping("/users-entity")
public class UsersController {
    @Autowired
    private UsersService usersService;


    /**
     * 登录
     * @param usersModel
     * @param request
     * @return
     */
    @ApiOperation(value = "登录后返回 token")
    @PostMapping("/login")
    public CRModel login(@RequestBody UsersModel usersModel, HttpServletRequest request) {
        return usersService.login(usersModel, request);
    }

    /**
     * 用户注册
     * @param usersModel
     * @return
     */
    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public CRModel register(@RequestBody UsersModel usersModel) {
        return usersService.addOneUser(usersModel);
    }

    /**
     * 用户获取短信验证码
     * @return
     */
    @ApiOperation(value = "获取短信验证码")
    @GetMapping("/get-sms-code/{phone}")
    public CRModel getSmsCode(@PathVariable("phone") String phone) {
        return usersService.getSmsCode(phone);
    }

    /**
     * 验证用户名是否重复
     * @return
     */
    @ApiOperation(value = "用户名是否重复")
    @GetMapping("/username-existed/{username}")
    public CRModel usernameExisted(@PathVariable("username") String username) {
        return usersService.usernameExisted(username);
    }
    /**
     * 获取当前用户信息
     * @param principal
     * @return
     */
    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping("/get-current-user-info")
    public CRModel getCurrentUserInfo(Principal principal) {
        if (null == principal) {
            return null;
        }
        String username = principal.getName();
        UsersEntity usersEntity = usersService.getCurrentUserInfo(username);
        //不能返回用户密码
        usersEntity.setUserPassword(null);
        return CRModel.success(StatusCode.SUCCESS,Message.SUCCESS, usersEntity);
    }

    /**
     * 注销登录
     * @return
     */
    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public CRModel logout() {
        return CRModel.success(StatusCode.SUCCESS,Message.SUC_LOGOUT,null);
    }

    /**
     * 用户上传图片所需接口
     * @param key
     * @return
     */
    @ApiOperation(value = "用户获取图片上传 token")
    @GetMapping("/qiniu/uploadToken/{key}")
    public CRModel getQiniuToken(@PathVariable String key) {
        System.out.println(key);
        String uploadToken = QiniuToken.getBaseToken(QiniuToken.CLOUD_BUCKET,key);
        System.out.println(uploadToken);
        if (uploadToken == null){
            return CRModel.warning(StatusCode.WARNING, Message.WAR_WRONG_KEY, null);
        } else {
            return CRModel.success(StatusCode.SUCCESS, Message.SUCCESS,uploadToken);
        }
    }
}
