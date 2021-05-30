package com.ITIS.DreamTreeSharer.controller;


import com.ITIS.DreamTreeSharer.config.constants.Message;
import com.ITIS.DreamTreeSharer.config.constants.StatusCode;
import com.ITIS.DreamTreeSharer.entity.PinboardsEntity;
import com.ITIS.DreamTreeSharer.model.CRModel;
import com.ITIS.DreamTreeSharer.service.PinboardsService;
import com.ITIS.DreamTreeSharer.service.UsersPinboardsFavoritesService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private UsersPinboardsFavoritesService upfService;


    @ApiOperation(value = "添加一个 pinboard")
    @PostMapping("/add-one-pinboard")
    public CRModel addOnePinboard(@RequestBody PinboardsEntity pinboardsEntity) {
        CRModel rep;
        try {
            rep = pinService.addOnePinboard(pinboardsEntity);
        } catch (Exception e) {
            return new CRModel(StatusCode.ERROR, e.getMessage(), null);
        }
        return rep;
    }

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


    /**
     * 需要设置级联更新
     * @param pin
     * @return
     */
    @ApiOperation(value = "更新一个 pinboard ")
    @PutMapping("/update-pinboard")
    public CRModel updatePin(@RequestBody PinboardsEntity pin) {
        if (pinService.update(new UpdateWrapper<PinboardsEntity>()
                .eq("pinboard_id", pin.getPinboardId())
                .set("pinboard_title", pin.getPinboardTitle())
                .set("pinboard_content", pin.getPinboardContent())
                .set("pinboard_bgimg_url", pin.getPinboardBgimgUrl())
                .set("pinboard_sharable", pin.getPinboardSharable()))) {
            return new CRModel(StatusCode.SUCCESS, "更新"+ Message.SUCCESS, null);
        }
        return new CRModel(StatusCode.WARNING, "更新"+Message.WARNING, null);
    }

    @ApiOperation(value = "收藏一个 Pin")
    @PostMapping("/favorite-one-pin/{pinId}")
    public CRModel favoriteOnePin(@PathVariable String pinId) {
        return upfService.addOnePin(pinId);
    }

    @ApiOperation(value = "获取当前登录用户上传的 pinboard 列表")
    @GetMapping("/get-pinboards")
    public CRModel getPinboards() {
        return pinService.getPinboards();
    }

    @ApiOperation(value = "删除一个 pinboard")
    @DeleteMapping("/delete-one-pinboard/{pinboardId}")
    public CRModel deleteOnePinboard(@PathVariable("pinboardId") String pinboardId) {
        return pinService.deleteOnePinboardById(pinboardId);
    }


    @ApiOperation(value = "得到收藏夹里的 pin")
    @GetMapping("/get-favorites")
    public CRModel getFavorites() {
        return upfService.getFavorites();
    }

}
