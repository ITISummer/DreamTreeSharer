package com.ITIS.DreamTreeSharer.service;

import com.ITIS.DreamTreeSharer.entity.UsersPinboardsCommentsEntity;
import com.ITIS.DreamTreeSharer.model.CRModel;
import com.ITIS.DreamTreeSharer.model.CommentModel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户对愿望板的评论记录表 服务类
 * </p>
 *
 * @author SummerLv
 * @since 2021-03-26
 */
public interface UsersPinboardsCommentsService extends IService<UsersPinboardsCommentsEntity> {

    /**
     * 添加一条评论
     */
    CRModel addOneComment(CommentModel commentModel);

    /**
     * 分页获取评论
     */
    CRModel getComments(String pinId, int limit, int offset);

    /**
     * 更新评论点赞量
     */
    CRModel updateLikeNum(int commentId,int likeNum);
}
