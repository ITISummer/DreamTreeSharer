package com.ITIS.DreamTreeSharer.service;

import com.ITIS.DreamTreeSharer.entity.UsersPinboardsEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户对愿望板的操作记录表 服务类
 * </p>
 *
 * @author SummerLv
 * @since 2021-03-26
 */
public interface UsersPinboardsService extends IService<UsersPinboardsEntity> {

    Integer addOneUsersPinboardsRecord(String userId, String pinboardId);
}
