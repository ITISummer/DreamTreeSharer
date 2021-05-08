package com.ITIS.DreamTreeSharer.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName PinboardModel
 * @Author LCX
 * @Date 2021 2021-05-03 1:14 p.m.
 * @Version 1.0
 * 添加一个 pinboard
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "PinboardModel", description = "愿望板模型类，用于接收前台的数据模型")
public class PinboardModel implements Serializable {

    @ApiModelProperty(value = "愿望板标题", required = true)
    private String pinboardTitle;
    @ApiModelProperty(value = "愿望板内容", required = true)
    private String pinboardContent;
    @ApiModelProperty(value = "愿望板背景图", required = true)
    private String pinboardBgimgUrl;
    @ApiModelProperty(value = "愿望板是否分享 true-1 表示分享，false-0 表示不分享", required = true)
    private Boolean pinboardSharable;
    @ApiModelProperty(value = "愿望板类型")
    private String pinboardType;
}
