package com.ITIS.DreamTreeSharer.controller;


import com.ITIS.DreamTreeSharer.model.CRModel;
import com.ITIS.DreamTreeSharer.service.AdminsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author SummerLv
 * @since 2021-03-26
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "AdminController")
public class AdminsController {

    @Autowired
    private AdminsService adminsService;

    @ApiOperation(value = "查询所有用户")
    @GetMapping("/get-all-user")
    public CRModel getAllUsers() {
        System.out.println("==============");
        return adminsService.getAllUser(); }

    @ApiOperation(value = "禁用用户账户")
    @PutMapping("/disable-a-user/{userId}")
    public CRModel disableAUsers(@PathVariable String userId) {
        return adminsService.disableAUsers(userId);
    }

    @ApiOperation(value = "查询所有 pinboard ")
    @GetMapping("/get-all-pin")
    public CRModel getAllPins() {
        return adminsService.getAllPin();
    }

    @ApiOperation(value = "删除一个 pinboard ")
    @DeleteMapping("/delete-a-pin/{pinId}")
    public CRModel deleteAPin(@PathVariable String pinId) {
       return adminsService.deleteAPin(pinId);
    }

    @ApiOperation(value = "查询所有pin下的评论以及作者 ")
    @GetMapping("/get-all-comment")
    public CRModel getAllComments() {
        return adminsService.getAllComment();
    }

    @ApiOperation(value = "删除一条评论")
    @DeleteMapping("/delete-a-comment/{commentId}")
    public CRModel deleteAComment(@PathVariable int commentId) {
        return adminsService.deleteAComment(commentId);
    }
}
