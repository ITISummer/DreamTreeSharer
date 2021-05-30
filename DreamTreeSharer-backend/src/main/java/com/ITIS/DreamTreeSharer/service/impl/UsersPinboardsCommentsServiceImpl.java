package com.ITIS.DreamTreeSharer.service.impl;

import com.ITIS.DreamTreeSharer.config.constants.Message;
import com.ITIS.DreamTreeSharer.config.constants.StatusCode;
import com.ITIS.DreamTreeSharer.entity.UsersPinboardsCommentsEntity;
import com.ITIS.DreamTreeSharer.dao.UsersPinboardsCommentsDao;
import com.ITIS.DreamTreeSharer.model.CRModel;
import com.ITIS.DreamTreeSharer.model.CommentModel;
import com.ITIS.DreamTreeSharer.service.UsersPinboardsCommentsService;
import com.ITIS.DreamTreeSharer.utils.UsersUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户对愿望板的评论记录表 服务实现类
 * </p>
 *
 * @author SummerLv
 * @since 2021-03-26
 */
@Service
public class UsersPinboardsCommentsServiceImpl extends ServiceImpl<UsersPinboardsCommentsDao, UsersPinboardsCommentsEntity> implements UsersPinboardsCommentsService {

    @Autowired
    private UsersPinboardsCommentsDao upcDao;

    @Override
    public CRModel addOneComment(CommentModel commentModel) {
        String userId = UsersUtil.getCurrentUser().getUserId();
        int res = upcDao.addOneComment(userId, commentModel);
        return res == 1 ?
                new CRModel(StatusCode.SUCCESS, "添加评论" + Message.SUCCESS, commentModel.getCommentId()) :
                new CRModel(StatusCode.WARNING, "添加评论" + Message.WARNING, null);
    }

    @Override
    public CRModel getComments(String pinId, int limit, int offset) {
        List<CommentModel> res = upcDao.getComments(pinId, limit, offset);
        return res.size() > 0 ? new CRModel(StatusCode.SUCCESS, "", res) :
                new CRModel(StatusCode.WARNING, "该愿望卡下没有评论！", null);
    }

    @Override
    public CRModel updateLikeNum(int commentId, int likeNum) {
        int res = upcDao.update(null, new UpdateWrapper<UsersPinboardsCommentsEntity>().eq("upc_id", commentId).set("like_num", likeNum));
        return res >= 1 ? new CRModel(StatusCode.SUCCESS, "", null) :
                new CRModel(StatusCode.WARNING, "点赞" + Message.WARNING, null);
    }
}
