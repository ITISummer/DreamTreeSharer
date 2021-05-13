package com.ITIS.DreamTreeSharer.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName UPModel
 * @Author LCX
 * @Date 2021 2021-05-09 9:46 a.m.
 * @Version 1.0
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "UPModel", description = "user-pinboard 实体类，用于构建 Home 页面数据")
public class UPModel implements Serializable {
    private static final long serialVersionUID = 1L;
// user 所需字段
    @ApiModelProperty(value = "pinboard 作者id")
    private String userId;
    @ApiModelProperty(value = "pinboard 作者用户名")
    private String userUsername;
    @ApiModelProperty(value = "pinboard 作者头像")
    private String userAvatarUrl;
// pinboard 所需字段
    @ApiModelProperty(value = "愿望板id")
    private String pinboardId;
    @ApiModelProperty(value = "愿望板名")
    private String pinboardTitle;
    @ApiModelProperty(value = "愿望板内容")
    private String pinboardContent;
    @ApiModelProperty(value = "创建愿望板时间")
    private LocalDateTime pinboardCreateTime;
    @ApiModelProperty(value = "愿望板背景图地址")
    private String pinboardBgimgUrl;
    @ApiModelProperty(value = "愿望板类型")
    private String pinboardType;
    @ApiModelProperty(value = "是否分享愿望板")
    private Boolean pinboardSharable;
    @ApiModelProperty(value = "点赞量")
    private Integer likeNum;
// pinboard 收藏量
    @ApiModelProperty(value = "愿望板收藏量")
//    private Integer favoriteNum;
    private int favoriteNum;
}
