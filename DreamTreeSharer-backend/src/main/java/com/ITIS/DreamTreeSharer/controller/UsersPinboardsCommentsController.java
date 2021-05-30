package com.ITIS.DreamTreeSharer.controller;


import com.ITIS.DreamTreeSharer.model.CRModel;
import com.ITIS.DreamTreeSharer.model.CommentModel;
import com.ITIS.DreamTreeSharer.service.UsersPinboardsCommentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户对愿望板的评论记录表 前端控制器
 * </p>
 *
 * @author SummerLv
 * @since 2021-03-26
 */
@RestController
@Api(tags = "UsersPinboardsCommentsController")
public class UsersPinboardsCommentsController {

    @Autowired
    private UsersPinboardsCommentsService upcService;

    @ApiOperation(value = "多表连查与分页得到 CommentModel 实体 ")
    @GetMapping("/get-comments/{pinId}/{limit}/{offset}")
    public CRModel getComments(@PathVariable String pinId, @PathVariable int limit, @PathVariable int offset) {
        System.out.println(pinId+"---"+limit+"---"+offset);
        return upcService.getComments(pinId, limit, offset);
    }

    @ApiOperation(value = "更新用户评论点赞数")
    @PutMapping("/update-like-num/{commentId}/{likeNum}")
    public CRModel updateLikeNum(@PathVariable int commentId, @PathVariable int likeNum) {
        return upcService.updateLikeNum(commentId,likeNum);
    }


    @ApiOperation(value = "添加一条 comment ")
    @PostMapping("/add-one-comment")
    public CRModel addOneComment(@RequestBody CommentModel commentModel) {
        return upcService.addOneComment(commentModel);
    }
}
