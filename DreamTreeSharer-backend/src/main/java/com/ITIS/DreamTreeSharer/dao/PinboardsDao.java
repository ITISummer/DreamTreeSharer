package com.ITIS.DreamTreeSharer.dao;

import com.ITIS.DreamTreeSharer.entity.PinboardsEntity;
import com.ITIS.DreamTreeSharer.model.UPModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 愿望板表 Mapper 接口
 * </p>
 *
 * @author SummerLv
 * @since 2021-03-26
 */
@Repository
public interface PinboardsDao extends BaseMapper<PinboardsEntity> {

    List<PinboardsEntity> getPinboardsByuserId(@Param("userId") String userId);

    int deletePinboardById(@Param("userId")String userId, @Param("pinboardId") String pinboardId);

    List<UPModel> getSharablePins();
}
