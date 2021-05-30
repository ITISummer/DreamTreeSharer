package com.ITIS.DreamTreeSharer.service.impl;

import com.ITIS.DreamTreeSharer.config.constants.Message;
import com.ITIS.DreamTreeSharer.config.constants.StatusCode;
import com.ITIS.DreamTreeSharer.dao.PinboardsDao;
import com.ITIS.DreamTreeSharer.entity.PinboardsEntity;
import com.ITIS.DreamTreeSharer.entity.UsersPinboardsCommentsEntity;
import com.ITIS.DreamTreeSharer.entity.UsersPinboardsEntity;
import com.ITIS.DreamTreeSharer.model.CRModel;
import com.ITIS.DreamTreeSharer.model.UPModel;
import com.ITIS.DreamTreeSharer.service.PinboardsService;
import com.ITIS.DreamTreeSharer.service.UsersPinboardsService;
import com.ITIS.DreamTreeSharer.utils.UsersUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
    private PinboardsDao pinDao;
    @Autowired
    private UsersPinboardsService usersPinboardsService;

    /**
     * [Spring Boot 中使用 @Transactional 注解配置事务管理]
     * (https://blog.csdn.net/nextyu/article/details/78669997)
     */
    @Override
    @Transactional
    public CRModel addOnePinboard(PinboardsEntity pinboardsEntity) throws Exception {
        String pinboardId = UUID.randomUUID()+"";
        pinboardsEntity.setPinboardId(pinboardId);
        if (pinDao.insert(pinboardsEntity) == 1) {
            // 得到当前登录的用户 id
            String userId = UsersUtil.getCurrentUser().getUserId();
            if (usersPinboardsService.addOneUsersPinboardsRecord(userId,pinboardId) != 1){
                throw new Exception("PinboardsServiceImpl -> addOnePinboard() -> 插入 pinboard 失败！");
            };
            return new CRModel(StatusCode.SUCCESS, "添加"+Message.SUCCESS, pinboardId);
        } else {
            return new CRModel(StatusCode.WARNING, Message.WARNING, null);
        }
    }

    @Override
    public CRModel getPinboards() {
        String userId = UsersUtil.getCurrentUser().getUserId();
        List<PinboardsEntity> pinboards = pinDao.getPinboardsByuserId(userId);
        if (pinboards != null) {
            return new CRModel(StatusCode.SUCCESS, "", pinboards);
        }
        return new CRModel(StatusCode.WARNING, Message.WARNING, null);
    }

    @Override
    public CRModel deleteOnePinboardById(String pinboardId) {
        int res = pinDao.deletePinboardById(pinboardId);
        if (res > 0) {
            return new CRModel(StatusCode.SUCCESS,"删除"+Message.SUCCESS,null);
        }
        return new CRModel(StatusCode.WARNING,"删除"+Message.WARNING,null);
    }

    @Override
    public CRModel getSharablePinboard() {
        List<UPModel> sharablePins = pinDao.getSharablePins();
        if (sharablePins.size() >= 1) {
            return new CRModel(StatusCode.SUCCESS, "",sharablePins);
        }
        return new CRModel(StatusCode.WARNING, "主页加载"+Message.WARNING,null);
    }

    @Override
    public CRModel updateLikeNum(String pinId, int likeNum) {
        int res = pinDao.update(null, new UpdateWrapper<PinboardsEntity>().eq("pinboard_id",pinId).set("like_num",likeNum));
        return res>=1? new CRModel(StatusCode.SUCCESS,"",null):
                new CRModel(StatusCode.WARNING,"更新点赞"+Message.WARNING,null);
    }
}
