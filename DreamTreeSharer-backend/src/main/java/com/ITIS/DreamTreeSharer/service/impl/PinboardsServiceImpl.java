package com.ITIS.DreamTreeSharer.service.impl;

import com.ITIS.DreamTreeSharer.config.constants.Message;
import com.ITIS.DreamTreeSharer.config.constants.StatusCode;
import com.ITIS.DreamTreeSharer.dao.PinboardsDao;
import com.ITIS.DreamTreeSharer.entity.PinboardsEntity;
import com.ITIS.DreamTreeSharer.model.CRModel;
import com.ITIS.DreamTreeSharer.model.UPModel;
import com.ITIS.DreamTreeSharer.service.PinboardsService;
import com.ITIS.DreamTreeSharer.service.UsersPinboardsService;
import com.ITIS.DreamTreeSharer.utils.UsersUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 愿望板表 服务实现类
 * </p>
 *
 * @author SummerLv
 * @since 2021-03-26
 */
@Service
public class PinboardsServiceImpl extends ServiceImpl<PinboardsDao, PinboardsEntity> implements PinboardsService {

    @Autowired
    PinboardsDao pinboardsDao;
    @Autowired
    UsersPinboardsService usersPinboardsService;

    //[Spring Boot 中使用 @Transactional 注解配置事务管理](https://blog.csdn.net/nextyu/article/details/78669997)
    @Override
    @Transactional
    public CRModel addOnePinboard(PinboardsEntity pinboardsEntity) throws Exception {
        String pinboardId = UUID.randomUUID()+"";
        pinboardsEntity.setPinboardId(pinboardId);
        if (pinboardsDao.insert(pinboardsEntity) == 1) {
            // 得到当前登录的用户 id
            String userId = UsersUtil.getCurrentUser().getUserId();
            if (usersPinboardsService.addOneUsersPinboardsRecord(userId,pinboardId) != 1){
                throw new Exception("PinboardsServiceImpl -> addOnePinboard() -> 插入 pinboard 失败！");
            };
            return new CRModel(StatusCode.SUCCESS, Message.SUCCESS, pinboardId);
        } else {
            return new CRModel(StatusCode.WARNING, Message.WARNING, null);
        }
    }

    @Override
    public CRModel getPinboards() {
        String userId = UsersUtil.getCurrentUser().getUserId();
        List<PinboardsEntity> pinboards = pinboardsDao.getPinboardsByuserId(userId);
        if (pinboards != null) {
            return new CRModel(StatusCode.SUCCESS, Message.SUCCESS, pinboards);
        }
        return new CRModel(StatusCode.WARNING, Message.WARNING, null);
    }

    @Override
    public CRModel deleteOnePinboardById(String pinboardId) {
        int res = pinboardsDao.deletePinboardById(pinboardId);
        if (res > 0) {
            return new CRModel(StatusCode.SUCCESS,"删除"+Message.SUCCESS,null);
        }
        return new CRModel(StatusCode.WARNING,"删除"+Message.WARNING,null);
    }

    @Override
    public CRModel getSharablePinboard() {
        List<UPModel> sharablePins = pinboardsDao.getSharablePins();
        if (sharablePins.size() >= 1) {
            return new CRModel(StatusCode.SUCCESS, Message.SUCCESS,sharablePins);
        }
        return new CRModel(StatusCode.WARNING, Message.WARNING,null);
    }
}
