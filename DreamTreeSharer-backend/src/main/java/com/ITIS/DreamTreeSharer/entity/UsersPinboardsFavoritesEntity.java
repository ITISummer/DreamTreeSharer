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
 * 用户对愿望板的收藏记录表
 * </p>
 *
 * @author SummerLv
 * @since 2021-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("users_pinboards_favorites")
@ApiModel(value="UsersPinboardsFavoritesEntity对象", description="用户对愿望板的收藏记录表")
public class UsersPinboardsFavoritesEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户-愿望板-收藏id")
    @TableId
    private Integer upfId;

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
