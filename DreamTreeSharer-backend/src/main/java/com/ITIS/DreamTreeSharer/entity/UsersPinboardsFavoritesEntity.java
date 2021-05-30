package com.ITIS.DreamTreeSharer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@ApiModel(value = "UsersPinboardsFavoritesEntity", description = "用户对愿望板的收藏记录表")
public class UsersPinboardsFavoritesEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户-愿望板-收藏id")
    @TableId(type = IdType.AUTO)
    private Integer upfId;
    @ApiModelProperty(value = "用户id")
    private String userId;
    @ApiModelProperty(value = "愿望板id")
    private String pinboardId;
    @ApiModelProperty(value = "创建愿望板时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime upCreateTime;


}
