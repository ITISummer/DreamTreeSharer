package com.ITIS.DreamTreeSharer.service.impl;

import com.ITIS.DreamTreeSharer.config.common.Message;
import com.ITIS.DreamTreeSharer.config.common.StatusCode;
import com.ITIS.DreamTreeSharer.entity.UsersPinboardsFavoritesEntity;
import com.ITIS.DreamTreeSharer.dao.UsersPinboardsFavoritesDao;
import com.ITIS.DreamTreeSharer.model.CRModel;
import com.ITIS.DreamTreeSharer.service.UsersPinboardsFavoritesService;
import com.ITIS.DreamTreeSharer.utils.UsersUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户对愿望板的收藏记录表 服务实现类
 * </p>
 *
 * @author SummerLv
 * @since 2021-03-26
 */
@Service
public class UsersPinboardsFavoritesServiceImpl extends ServiceImpl<UsersPinboardsFavoritesDao, UsersPinboardsFavoritesEntity> implements UsersPinboardsFavoritesService {

    @Autowired
    private UsersPinboardsFavoritesDao upfDao;

    /*
    [MybatisPlus中插入数据获取主键值](https://blog.csdn.net/BADAO_LIUMANG_QIZHI/article/details/89431498)
    [Mybatis-plus argument type mismatch](https://blog.csdn.net/qq_16836791/article/details/112798951)
     */
    @Override
    public CRModel addOnePin(String pinId) {
        // 判断用户是否已经对该 pin 进行了收藏
        String userId = UsersUtil.getCurrentUser().getUserId();
        if (upfDao.selectOne(new QueryWrapper<UsersPinboardsFavoritesEntity>().eq("user_id",userId))==null) {
            UsersPinboardsFavoritesEntity upfEntity = new UsersPinboardsFavoritesEntity();
            upfEntity.setUserId(userId);
            upfEntity.setPinboardId(pinId);
            return upfDao.insert(upfEntity) == 1?
                    new CRModel(StatusCode.SUCCESS, Message.SUCCESS,null):
                    new CRModel(StatusCode.WARNING, Message.WARNING,null);
        }
        return new CRModel(StatusCode.WARNING, "您已收藏过此愿望卡！",null);
    }
}
