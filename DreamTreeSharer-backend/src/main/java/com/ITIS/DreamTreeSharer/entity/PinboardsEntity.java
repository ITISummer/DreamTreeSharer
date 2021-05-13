package com.ITIS.DreamTreeSharer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 愿望板表
 * </p>
 *
 * @author SummerLv
 * @since 2021-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pinboards")
@ApiModel(value="PinboardsEntity对象", description="愿望板表")
public class PinboardsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "愿望板id")
    @TableId
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
}
