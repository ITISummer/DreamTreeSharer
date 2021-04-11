package com.ITIS.DreamTreeSharer.model;

import com.ITIS.DreamTreeSharer.config.common.StatusCode;
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
     * 成功返回结果
     * @param message 返回信息
     */
    public static CRModel success(String message) {
        return new CRModel(StatusCode.SUCCESS,message,null);
    }

    /**
     * 成功返回结果
     * @param message 返回信息
     * @param obj 返回对象
     */
    public static CRModel success(String message, Object obj) {
        return new CRModel(StatusCode.SUCCESS,message,obj);
    }

    /**
     * 成功返回结果
     * @param statusCode  错误码
     * @param message 错误信息
     */
    public static CRModel success(Integer statusCode, String message) {
        return new CRModel(statusCode, message, null);
    }

    /**
     * 失败返回结果
     * @param message 返回信息
     */
    public static CRModel error(String message) {
        return new CRModel(StatusCode.ERROR, message, null);
    }

    /**
     * 失败返回结果
     * @param message 返回信息
     * @param obj 返回对象
     */
    public static CRModel error(String message, Object obj) {
        return new CRModel(StatusCode.ERROR, message, obj);
    }

    /**
     * 失败返回结果
     * @param statusCode  错误码
     * @param message 错误信息
     */
    public static CRModel error(Integer statusCode, String message) {
        return new CRModel(statusCode, message, null);
    }


}
