package com.ITIS.DreamTreeSharer.controller;


import com.ITIS.DreamTreeSharer.config.constants.Message;
import com.ITIS.DreamTreeSharer.config.constants.StatusCode;
import com.ITIS.DreamTreeSharer.entity.PinboardsEntity;
import com.ITIS.DreamTreeSharer.entity.UsersEntity;
import com.ITIS.DreamTreeSharer.model.CRModel;
import com.ITIS.DreamTreeSharer.model.CommentModel;
import com.ITIS.DreamTreeSharer.model.UsersModel;
import com.ITIS.DreamTreeSharer.service.*;
import com.ITIS.DreamTreeSharer.utils.QiniuToken;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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
    private PinboardsService pinboardsService;
    @Autowired
    private UsersPinboardsService usersPinboardsService;
    @Autowired
    private UsersPinboardsCommentsService upcService;
    @Autowired
    private UsersPinboardsFavoritesService upfService;

    @ApiOperation(value = "根据用户名获取用户信息")
    @GetMapping("/get-user-info/{username}")
    public CRModel getUserInfo(@PathVariable String username) {
        UsersEntity user = (UsersEntity) usersService.loadUserByUsername(username);
        user.setUserPassword(null);
        return new CRModel(StatusCode.SUCCESS, Message.SUCCESS, user);
    }

    @ApiOperation(value = "得到收藏夹里的 pin")
    @GetMapping("/get-favorites")
    public CRModel getFavorites() {
        return upfService.getFavorites();
    }

    @ApiOperation(value = "模糊查询")
    @GetMapping("/fuzzy-search/{flag}/{search}/{limit}/{offset}")
    public CRModel fuzzySearch(@PathVariable String flag, @PathVariable String search, @PathVariable int limit, @PathVariable int offset) {
        return usersService.fuzzySearch(flag, search, limit, offset);
    }

    @ApiOperation(value = "收藏一个 Pin")
    @PostMapping("/favorite-one-pin/{pinId}")
    public CRModel favoriteOnePin(@PathVariable String pinId) {
        return upfService.addOnePin(pinId);
    }

    @ApiOperation(value = "添加一条 comment ")
    @PostMapping("/add-one-comment")
    public CRModel addOneComment(@RequestBody CommentModel commentModel) {
        return upcService.addOneComment(commentModel);
    }

    @ApiOperation(value = "更新一个 pinboard ")
    @PutMapping("/update-pinboard")
    public CRModel updatePin(@RequestBody PinboardsEntity pin) {
        if (pinboardsService.update(new UpdateWrapper<PinboardsEntity>()
                .eq("pinboard_id", pin.getPinboardId())
                .set("pinboard_title", pin.getPinboardTitle())
                .set("pinboard_content", pin.getPinboardContent())
                .set("pinboard_bgimg_url", pin.getPinboardBgimgUrl())
                .set("pinboard_sharable", pin.getPinboardSharable()))) {
            return new CRModel(StatusCode.SUCCESS, Message.SUCCESS, null);
        }
        return new CRModel(StatusCode.WARNING, Message.WARNING, null);
    }

    @ApiOperation(value = "获取当前登录用户上传的 pinboard 列表")
    @GetMapping("/get-pinboards")
    public CRModel getPinboards() {
        return pinboardsService.getPinboards();
    }

    @ApiOperation(value = "删除一个 pinboard")
    @DeleteMapping("/delete-one-pinboard/{pinboardId}")
    public CRModel deleteOnePinboard(@PathVariable("pinboardId") String pinboardId) {
        return pinboardsService.deleteOnePinboardById(pinboardId);
    }

    /**
     * 在每次做更新操作时应该重新设置spring security中的Authentication中的user信息
     * 这里由于是单独更新某项信息，没传入整个 UsersEntity 所以没重新设置 Authentication
     * 不过有三种策略来更新：
     * 1. 获取 Authentication 中的 user，然后单独设置更新项
     * 2. 重新从数据库中查询更新后的 user 信息
     * 3. 前端传入整个 UsersEntity
     * @param newAvatarUrl
     * @return
     */
    @ApiOperation(value = "更新用户头像")
    @PutMapping("/update-avatar-url/{newAvatarUrl}")
    public CRModel updateAvatar(@PathVariable("newAvatarUrl") String newAvatarUrl) {
        return usersService.updateAvatar(newAvatarUrl);
    }

    /**
     * [Spring’s RequestBody and ResponseBody Annotations]
     * (https://www.baeldung.com/spring-request-response-body)
     *
     * @param pwd
     * @return
     */
    @ApiOperation(value = "更新用户密码")
    @PutMapping("/update-pwd")
    public CRModel updatePwd(@RequestBody UsersModel usersModel) {
        return usersService.updatePwd(usersModel.getPassword());
    }



    // [SpringBoot获取参数的几种方式](https://www.jianshu.com/p/ee150654f712)
    @ApiOperation(value = "更新用户邮箱或者手机号")
    @PutMapping("/update-email-or-mobile/{flag}/{emailOrMobile}/{code}")
    public CRModel updateEmailOrMobile(@PathVariable("flag") String flag, @PathVariable("emailOrMobile") String emailOrMobile, @PathVariable("code") String code) {
        return usersService.updateEmailOrMobile(flag, emailOrMobile, code);
    }

    @ApiOperation(value = "根据关键词获取用户聊天列表")
    @GetMapping("/get-user-list")
    public CRModel getUserList(String keywords) {
        List<UsersEntity> users = usersService.getUserList(keywords);
        if (users != null) {
            return new CRModel(StatusCode.SUCCESS, Message.SUCCESS, users);
        } else {
            return new CRModel(StatusCode.WARNING, Message.WAR_NOT_FOUND_USER, null);
        }
    }

    @ApiOperation(value = "添加一个 pinboard")
    @PostMapping("/add-one-pinboard")
    public CRModel addOnePinboard(@RequestBody PinboardsEntity pinboardsEntity) {
        CRModel rep;
        try {
            rep = pinboardsService.addOnePinboard(pinboardsEntity);
        } catch (Exception e) {
            return new CRModel(StatusCode.ERROR, e.getMessage(), null);
        }
        return rep;
    }

    /**
     * 登录
     *
     * @param usersModel
     * @param request
     * @return
     */
    @ApiOperation(value = "登录后返回 token")
    @PostMapping("/login")
    public CRModel login(@RequestBody UsersModel usersModel) {
        System.out.println(usersModel);
        return usersService.login(usersModel);
    }

    /**
     * 用户注册
     *
     * @param usersModel
     * @return
     */
    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public CRModel register(@RequestBody UsersModel usersModel) {
        return usersService.addOneUser(usersModel);
    }


    /**
     * 验证用户名是否重复
     *
     * @return
     */
    @ApiOperation(value = "用户名是否重复")
    @GetMapping("/username-existed/{username}")
    public CRModel usernameExisted(@PathVariable("username") String username) {
        return usersService.usernameExisted(username);
    }

    /**
     * 获取当前用户信息
     *
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
        return CRModel.success(StatusCode.SUCCESS, Message.SUCCESS, usersEntity);
    }

    /**
     * 注销登录
     *
     * @return
     */
    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public CRModel logout() {
        return CRModel.success(StatusCode.SUCCESS, Message.SUC_LOGOUT, null);
    }

    /**
     * 用户上传图片所需接口
     *
     * @param key
     * @return
     */
    @ApiOperation(value = "用户获取图片上传 token")
    @GetMapping("/qiniu/uploadToken/{key}")
    public CRModel getQiniuToken(@PathVariable String key) {
        String uploadToken = QiniuToken.getBaseToken(QiniuToken.CLOUD_BUCKET, key);
        if (uploadToken == null) {
            return CRModel.warning(StatusCode.WARNING, Message.WAR_WRONG_KEY, null);
        } else {
            return CRModel.success(StatusCode.SUCCESS, Message.SUCCESS, uploadToken);
        }
    }
}
