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
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public CRModel getAllUser(int currentPage,int size) {
        QueryWrapper<UsersEntity> wrapper = new QueryWrapper<>();
        long count = usersDao.selectCount(wrapper);
        IPage<UsersEntity> page = new Page<>(currentPage, size, count);
        page.setTotal(count);
        IPage<UsersEntity> userList = usersDao.selectPage(page, wrapper);
        if ( userList != null) {
            return new CRModel(StatusCode.SUCCESS, "",userList);
        }
        return new CRModel(StatusCode.WARNING, "获取所有用户"+Message.WARNING,null);
    }

    @Override
    public CRModel getAllPin(int currentPage,int size) {
//        QueryWrapper<UPModel> wrapper = new QueryWrapper<>();
//        long count = pinsDao.selectCount(wrapper);
//        IPage<UPModel> page = new Page<>(currentPage, size, count);
//        page.setTotal(count);
//        IPage<UPModel> pinList = pinsDao.selectPage(page,wrapper);
        Integer total = pinsDao.getAllPinCount();
        if (total > 0) {
            Map<String,Object> res = new HashMap<>();
            res.put("total",total);
            List<UPModel> pinList = pinsDao.getAllPin((currentPage-1)*size,size);
            if (pinList != null) {
                res.put("records",pinList);
                return new CRModel(StatusCode.SUCCESS,"",res);
            }
        }
        return new CRModel(StatusCode.WARNING, "获取所有愿望卡"+Message.WARNING,null);
    }

    @Override
    public CRModel getAllComment(int currentPage, int size) {
//        QueryWrapper<UPCModel> wrapper = new QueryWrapper<>();
//        long count = upcDao.selectCount(wrapper);
//        IPage<UPCModel> page = new Page<>(currentPage, size, count);
//        page.setTotal(count);
//        IPage<UPCModel> commentList = upcDao.selectPage(page, wrapper);
        Integer total = upcDao.getAllCommentCount();
        if (total > 0) {
            Map<String,Object> res = new HashMap<>();
            res.put("total",total);
            List<UPCModel> commentList = upcDao.getAllComment((currentPage-1)*size,size);
            if (commentList != null) {
                res.put("records",commentList);
                return new CRModel(StatusCode.SUCCESS,"",res);
            }
        }
        return new CRModel(StatusCode.WARNING, "获取所有评论"+Message.WARNING,null);
    }

    @Override
    public CRModel disableAUser(String userId,boolean enable) {
        return new CRModel(StatusCode.SUCCESS,"更新"+Message.SUCCESS,usersDao.update(null,new UpdateWrapper<UsersEntity>().eq("user_id",userId).set("user_enabled",enable)));
    }

    @Override
    public CRModel deleteAPin(String pinId) {
       return new CRModel(StatusCode.SUCCESS,"删除愿望卡"+Message.SUCCESS,pinsDao.deletePinboardById(pinId));
    }

    @Override
    public CRModel deleteAComment(int commentId) {
        return new CRModel(StatusCode.SUCCESS,"删除评论"+Message.SUCCESS,upcDao.deleteById(commentId));
    }
}
