package com.ITIS.DreamTreeSharer.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 消息类 - 用于封装消息的接收与发送
 *
 * @author LCX
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChatMsgModel {

	@ApiModelProperty(value = "发送者")
	private String from;
	@ApiModelProperty(value = "接收者")
	private String to;
	@ApiModelProperty(value = "发送内容")
	private String content;
	@ApiModelProperty(value = "发送时间")
	private LocalDateTime date;
	@ApiModelProperty(value = "发送者昵称")
	private String fromNickName;

}