package com.ITIS.DreamTreeSharer.dao;

import com.ITIS.DreamTreeSharer.entity.UsersEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author SummerLv
 * @since 2021-03-26
 */
@Repository
public interface UsersDao extends BaseMapper<UsersEntity> {
	/**
	 * 根据关键词获取用户列表
	 */
	List<UsersEntity> getUserList(@Param("id") String id, @Param("keywords") String keywords);

	/**
	 * 更新用户邮箱或者手机号
	 */
	int updateEmailOrMobile(@Param("id") String id, @Param("flag") String flag, @Param("emailOrMobile") String emailOrMobile);
}
