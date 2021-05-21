package com.ITIS.DreamTreeSharer.service.impl;

import com.ITIS.DreamTreeSharer.config.constants.Message;
import com.ITIS.DreamTreeSharer.config.constants.StatusCode;
import com.ITIS.DreamTreeSharer.dao.AdminsDao;
import com.ITIS.DreamTreeSharer.dao.PinboardsDao;
import com.ITIS.DreamTreeSharer.dao.UsersDao;
import com.ITIS.DreamTreeSharer.dao.UsersPinboardsCommentsDao;
import com.ITIS.DreamTreeSharer.entity.AdminsEntity;
import com.ITIS.DreamTreeSharer.entity.UsersEntity;
import com.ITIS.DreamTreeSharer.model.CRModel;
import com.ITIS.DreamTreeSharer.model.UPCModel;
import com.ITIS.DreamTreeSharer.model.UPModel;
import com.ITIS.DreamTreeSharer.service.AdminsService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author SummerLv
 * @since 2021-03-26
 */
@Service
public class AdminsServiceImpl extends ServiceImpl<AdminsDao, AdminsEntity> implements AdminsService {

    @Autowired
    private UsersDao usersDao;
    @Autowired
    private PinboardsDao pinsDao;
    @Autowired
    private UsersPinboardsCommentsDao upcDao;


    @Override
    public CRModel getAllUser() {
        List<UsersEntity> userList = usersDao.selectList(null);
        return new CRModel(StatusCode.SUCCESS, Message.SUCCESS,userList);
    }

    @Override
    public CRModel getAllPin() {
        List<UPModel> pinList = pinsDao.getAllPin();
        return new CRModel(StatusCode.SUCCESS, Message.SUCCESS,pinList);
    }

    @Override
    public CRModel getAllComment() {
        List<UPCModel> commentList = upcDao.getAllComment();
        return new CRModel(StatusCode.SUCCESS,Message.SUCCESS,commentList);
    }

    @Override
    public CRModel disableAUsers(String userId) {
        return new CRModel(StatusCode.SUCCESS,Message.SUCCESS,usersDao.update(null,new UpdateWrapper<UsersEntity>().eq("user_id",userId).set("user_enabled",false)));
    }

    @Override
    public CRModel deleteAPin(String pinId) {
       return new CRModel(StatusCode.SUCCESS,Message.SUCCESS,pinsDao.deletePinboardById(pinId));
    }

    @Override
    public CRModel deleteAComment(int commentId) {
        return new CRModel(StatusCode.SUCCESS,Message.SUCCESS,upcDao.deleteById(commentId));
    }
}
