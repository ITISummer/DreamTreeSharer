package com.ITIS.DreamTreeSharer.service.impl;

import com.ITIS.DreamTreeSharer.entity.UsersPinboardsEntity;
import com.ITIS.DreamTreeSharer.dao.UsersPinboardsDao;
import com.ITIS.DreamTreeSharer.service.UsersPinboardsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <p>
 * 用户对愿望板的操作记录表 服务实现类
 * </p>
 *
 * @author SummerLv
 * @since 2021-03-26
 */
@Service
public class UsersPinboardsServiceImpl extends ServiceImpl<UsersPinboardsDao, UsersPinboardsEntity> implements UsersPinboardsService {

    @Autowired
    private UsersPinboardsDao usersPinboardsDao;

    @Override
    public Integer addOneUsersPinboardsRecord(String userId, String pinboardId) {
        UsersPinboardsEntity usersPinboardsEntity = new UsersPinboardsEntity();
        usersPinboardsEntity.setUpId(UUID.randomUUID()+"");
        usersPinboardsEntity.setPinboardId(pinboardId);
        usersPinboardsEntity.setUserId(userId);
        return usersPinboardsDao.insert(usersPinboardsEntity);
    }
}
