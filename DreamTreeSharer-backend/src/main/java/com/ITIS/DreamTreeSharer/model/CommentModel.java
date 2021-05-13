package com.ITIS.DreamTreeSharer.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @ClassName CommentModel
 * @Author LCX
 * @Date 2021 2021-05-09 8:39 p.m.
 * @Version 1.0
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "CommentModel", description = "用户评论模型")
public class CommentModel {

    @ApiModelProperty(value = "用于接收返回的自增评论 id")
    private Integer commentId;
    @ApiModelProperty(value = "被评论图片 id")
    private String pinboardId;
    @ApiModelProperty(value = "评论者头像")
    private String fromAvatar;
    @ApiModelProperty(value = "评论者昵称")
    private String fromName;
    @ApiModelProperty(value = "评论发布时间")
    private String date;
    @ApiModelProperty(value = "评论内容")
    private String content;
    @ApiModelProperty(value = "点赞数")
    private Integer likeNum;
}
