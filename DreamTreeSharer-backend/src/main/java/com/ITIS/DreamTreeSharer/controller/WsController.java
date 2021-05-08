package com.ITIS.DreamTreeSharer.controller;

import com.ITIS.DreamTreeSharer.entity.ChatMsg;
import com.ITIS.DreamTreeSharer.entity.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

/**
 * websocket
 *
 * @author LCX
 * @since 1.0.0
 */
@Controller
public class WsController {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;


	@MessageMapping("/ws/chat")
	public void handleMsg(Authentication authentication, ChatMsg chatMsg){
		UsersEntity user = (UsersEntity) authentication.getPrincipal();
		chatMsg.setFrom(user.getUserUsername());
		chatMsg.setFormNickName(user.getUserUsername());
		chatMsg.setDate(LocalDateTime.now());
		simpMessagingTemplate.convertAndSendToUser(chatMsg.getTo(),"/queue/chat",chatMsg);
	}

}