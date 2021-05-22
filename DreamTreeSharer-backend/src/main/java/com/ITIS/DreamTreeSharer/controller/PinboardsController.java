package com.ITIS.DreamTreeSharer.controller;


import com.ITIS.DreamTreeSharer.model.CRModel;
import com.ITIS.DreamTreeSharer.service.PinboardsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 愿望板表 前端控制器
 * </p>
 *
 * @author SummerLv
 * @since 2021-03-26
 */
@RestController
@Api(tags = "PinboardsController")
public class PinboardsController {

    @Autowired
    private PinboardsService pinService;



    @ApiOperation(value = "Home 页获取用户分享的 pinboard")
    @GetMapping("/get-sharable-pin")
    public CRModel getSharablePinboard(){
        return pinService.getSharablePinboard();
    }


    @ApiOperation(value = "更新用户评论点赞数")
    @PutMapping("/update-pin-like-num/{pinId}/{likeNum}")
    public CRModel updateLikeNum(@PathVariable String pinId, @PathVariable int likeNum) {
        return pinService.updateLikeNum(pinId,likeNum);
    }

}
