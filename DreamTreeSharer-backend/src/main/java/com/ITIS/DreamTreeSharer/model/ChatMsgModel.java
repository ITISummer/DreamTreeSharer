package com.ITIS.DreamTreeSharer.model;

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

	// 发送者
	private String from;
	// 接收者
	private String to;
	// 发送内容
	private String content;
	// 发送时间
	private LocalDateTime date;
	// 发送者昵称
	private String fromNickName;

}