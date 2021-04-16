package com.ITIS.DreamTreeSharer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户对愿望板的评论记录表
 * </p>
 *
 * @author SummerLv
 * @since 2021-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("users_pinboards_comments")
@ApiModel(value="UsersPinboardsCommentsEntity对象", description="用户对愿望板的评论记录表")
public class UsersPinboardsCommentsEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "用户-愿望板-评论id")
    @TableId
    private Integer upcId;

    @ApiModelProperty(value = "用户id")
    @TableField
    private String userId;

    @ApiModelProperty(value = "愿望板id")
    @TableField
    private String pinboardId;

    @ApiModelProperty(value = "创建愿望板时间")
    @TableField
    private LocalDateTime upCreateTime;


}