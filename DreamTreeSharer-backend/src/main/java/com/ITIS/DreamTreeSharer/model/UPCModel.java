package com.ITIS.DreamTreeSharer.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName UPCModel
 * @Author LCX
 * @Date 2021 2021-05-15 11:14 a.m.
 * @Version 1.0
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "UPCModel", description = "users-pinboards-comments 实体类，用于构建管理员管理评论页面数据")
public class UPCModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "评论作者用户名")
    private String commentAuthor;
    @ApiModelProperty(value = "pinboard 作者用户名")
    private String pinAuthor;
    @ApiModelProperty(value = "pinboard 标题")
    private String pinboardTitle;
    @ApiModelProperty(value = "pinboard 内容")
    private String pinboardContent;
    @ApiModelProperty(value = "pinboard 背景图")
    private String pinboardBgimgUrl;
    @ApiModelProperty(value = "评论内容")
    private String comment;
    @ApiModelProperty(value = "评论创建时间")
    private LocalDateTime upCreateTime;
    @ApiModelProperty(value = "评论受点赞数量")
    private Integer likeNum;
}
