package com.ITIS.DreamTreeSharer.dao;

import com.ITIS.DreamTreeSharer.entity.UsersPinboardsLikesEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户对愿望板的点赞记录表 Mapper 接口
 * </p>
 *
 * @author SummerLv
 * @since 2021-03-26
 */
@Repository
public interface UsersPinboardsLikesDao extends BaseMapper<UsersPinboardsLikesEntity> {

}
