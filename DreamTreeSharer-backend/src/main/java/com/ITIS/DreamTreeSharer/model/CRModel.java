package com.ITIS.DreamTreeSharer.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName CRModel
 * @Author LCX
 * @Date 2021 2021-03-26 6:47 p.m.
 * @Version 1.0
 * 公共返回对象
 * [swagger 注解使用说明]https://blog.csdn.net/u014231523/article/details/76522486
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "CRModel", description = "公共返回对象")
public class CRModel {
    @ApiModelProperty(value = "状态码")
    private Integer statusCode;
    @ApiModelProperty(value = "提示信息")
    private String message;
    @ApiModelProperty(value = "返回对象")
    private Object object;

    /**
     * 成功信息 - [200,300) - 对应成功状态码范围
     */
    public static CRModel success(Integer statusCode, String message, Object obj) { return new CRModel(statusCode, message, obj); }
    /**
     * 提示信息 - [400,500) - 对应提示状态码范围
     */
    public static CRModel warning(Integer statusCode, String message, Object obj) {
        return new CRModel(statusCode, message, obj);
    }
    /**
     * 错误信息 - [500,600) - 对应错误状态码范围
     */
    public static CRModel error(Integer statusCode, String message, Object obj) {
        return new CRModel(statusCode, message, obj);
    }


}
