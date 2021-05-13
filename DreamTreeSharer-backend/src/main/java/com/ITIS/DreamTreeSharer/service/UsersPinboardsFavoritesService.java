package com.ITIS.DreamTreeSharer.service;

import com.ITIS.DreamTreeSharer.entity.UsersPinboardsFavoritesEntity;
import com.ITIS.DreamTreeSharer.model.CRModel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户对愿望板的收藏记录表 服务类
 * </p>
 *
 * @author SummerLv
 * @since 2021-03-26
 */
public interface UsersPinboardsFavoritesService extends IService<UsersPinboardsFavoritesEntity> {

    CRModel addOnePin(String pinId);
}
