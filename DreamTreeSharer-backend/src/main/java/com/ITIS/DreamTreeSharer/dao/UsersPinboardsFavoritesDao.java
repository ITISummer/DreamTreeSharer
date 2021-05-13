package com.ITIS.DreamTreeSharer.dao;

import com.ITIS.DreamTreeSharer.entity.UsersPinboardsFavoritesEntity;
import com.ITIS.DreamTreeSharer.model.UPModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户对愿望板的收藏记录表 Mapper 接口
 * </p>
 *
 * @author SummerLv
 * @since 2021-03-26
 */
@Repository
public interface UsersPinboardsFavoritesDao extends BaseMapper<UsersPinboardsFavoritesEntity> {

    List<UPModel> getFavoritesById(@Param("userId") String userId);
}
