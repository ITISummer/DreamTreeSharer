package com.ITIS.DreamTreeSharer.utils;

import com.ITIS.DreamTreeSharer.entity.UsersEntity;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @ClassName UsersUtil
 * @Author LCX
 * @Date 2021 2021-05-05 9:40 p.m.
 * @Version 1.0
 **/
public class UsersUtil {
    /**
	 * 获取当前登录操作员
	 *
	 * @return
	 */
	public static UsersEntity getCurrentUser() {
		return (UsersEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
