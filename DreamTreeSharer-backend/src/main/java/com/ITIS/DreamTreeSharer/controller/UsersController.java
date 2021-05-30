package com.ITIS.DreamTreeSharer.controller;


import com.ITIS.DreamTreeSharer.config.constants.Message;
import com.ITIS.DreamTreeSharer.config.constants.StatusCode;
import com.ITIS.DreamTreeSharer.entity.PinboardsEntity;
import com.ITIS.DreamTreeSharer.entity.UsersEntity;
import com.ITIS.DreamTreeSharer.model.CRModel;
import com.ITIS.DreamTreeSharer.model.UsersModel;
import com.ITIS.DreamTreeSharer.service.*;
import com.ITIS.DreamTreeSharer.utils.QiniuToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @ClassName UsersController
 * @Author LCX
 * @Date 2021 2021-03-26 9:22 p.m.
 * @Version 1.0
 **/
@RestController
@Api(tags = "UsersController")
public class UsersController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private PinboardsService pinService;
    @Autowired
    private UsersPinboardsService upService;

    @ApiOperation(value = "根据用户名获取用户信息")
    @GetMapping("/get-user-info/{username}")
    public CRModel getUserInfo(@PathVariable String username) {
        UsersEntity user = (UsersEntity) usersService.loadUserByUsername(username);
        if(user!=null) {
            user.setUserPassword(null);
            return new CRModel(StatusCode.SUCCESS, "", user);
        }
        return new CRModel(StatusCode.SUCCESS, "获取用户信息"+Message.WARNING, user);
    }



    @ApiOperation(value = "模糊查询")
    @GetMapping("/fuzzy-search/{flag}/{search}/{limit}/{offset}")
    public CRModel fuzzySearch(@PathVariable String flag, @PathVariable String search, @PathVariable int limit, @PathVariable int offset) {
        return usersService.fuzzySearch(flag, search, limit, offset);
    }


    /**
     * 在每次做更新操作时应该重新设置spring security中的Authentication中的user信息
     * 这里由于是单独更新某项信息，没传入整个 UsersEntity 所以没重新设置 Authentication
     * 不过有三种策略来更新：
     * 1. 获取 Authentication 中的 user，然后单独设置更新项
     * 2. 重新从数据库中查询更新后的 user 信息
     * 3. 前端传入整个 UsersEntity
     */
    @ApiOperation(value = "更新用户头像")
    @PutMapping("/update-avatar-url/{newAvatarUrl}")
    public CRModel updateAvatar(@PathVariable("newAvatarUrl") String newAvatarUrl) {
        return usersService.updateAvatar(newAvatarUrl);
    }

    /**
     * [Spring’s RequestBody and ResponseBody Annotations]
     * (https://www.baeldung.com/spring-request-response-body)
     */
    @ApiOperation(value = "更新用户密码")
    @PutMapping("/update-pwd")
    public CRModel updatePwd(@RequestBody UsersModel usersModel) {
        return usersService.updatePwd(usersModel.getPassword());
    }

    /**
     [SpringBoot获取参数的几种方式](https://www.jianshu.com/p/ee150654f712)
     */
    @ApiOperation(value = "更新用户邮箱或者手机号")
    @PutMapping("/update-email-or-mobile/{flag}/{emailOrMobile}/{code}")
    public CRModel updateEmailOrMobile(@PathVariable("flag") String flag, @PathVariable("emailOrMobile") String emailOrMobile, @PathVariable("code") String code) {
        return usersService.updateEmailOrMobile(flag, emailOrMobile, code);
    }

    @ApiOperation(value = "根据关键词获取用户聊天列表")
    @GetMapping("/get-chat-list")
    public CRModel getChatList(String keywords) {
      return usersService.getUserList(keywords);
    }


    @ApiOperation(value = "登录后返回 token")
    @PostMapping("/login")
    public CRModel login(@RequestBody UsersModel usersModel, HttpServletRequest request) {
        return usersService.login(usersModel,request);
    }

    /**
     * 用户注册
     */
    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public CRModel register(@RequestBody UsersModel usersModel) {
        return usersService.addOneUser(usersModel);
    }


    /**
     * 验证用户名是否重复
     */
    @ApiOperation(value = "用户名是否重复")
    @GetMapping("/username-existed/{username}")
    public CRModel usernameExisted(@PathVariable("username") String username) {
        return usersService.usernameExisted(username);
    }

    /**
     * 获取当前用户信息
     */
    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping("/get-current-user-info")
    public CRModel getCurrentUserInfo(Principal principal) {
        if (null == principal) {
            return CRModel.warning(StatusCode.WARNING, "获取用户信息"+Message.WARNING, null);
        }
        String username = principal.getName();
        UsersEntity usersEntity = usersService.getCurrentUserInfo(username);
        // 不能返回用户密码
        usersEntity.setUserPassword(null);
        return CRModel.success(StatusCode.SUCCESS, "", usersEntity);
    }

    /**
     * 注销登录
     */
    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public CRModel logout() {
        return CRModel.success(StatusCode.SUCCESS, Message.SUC_LOGOUT, null);
    }

    /**
     * 用户上传图片所需接口
     */
    @ApiOperation(value = "用户获取图片上传 token")
    @GetMapping("/qiniu/uploadToken/{key}")
    public CRModel getQiniuToken(@PathVariable String key) {
        String uploadToken = QiniuToken.getBaseToken(QiniuToken.CLOUD_BUCKET, key);
        if (uploadToken == null) {
            return CRModel.warning(StatusCode.WARNING, Message.WAR_WRONG_KEY, null);
        } else {
            return CRModel.success(StatusCode.SUCCESS, "", uploadToken);
        }
    }
}
