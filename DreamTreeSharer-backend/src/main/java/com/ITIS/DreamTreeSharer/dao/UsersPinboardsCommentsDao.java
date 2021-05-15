package com.ITIS.DreamTreeSharer.dao;

import com.ITIS.DreamTreeSharer.entity.UsersPinboardsCommentsEntity;
import com.ITIS.DreamTreeSharer.model.CRModel;
import com.ITIS.DreamTreeSharer.model.CommentModel;
import com.ITIS.DreamTreeSharer.model.UPCModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户对愿望板的评论记录表 Mapper 接口
 * </p>
 *
 * @author SummerLv
 * @since 2021-03-26
 */
@Repository
public interface UsersPinboardsCommentsDao extends BaseMapper<UsersPinboardsCommentsEntity> {

    int addOneComment(@Param("userId") String userId, @Param("commentModel") CommentModel commentModel);

    List<CommentModel> getComments(@Param("pinId") String pinId, @Param("limit") int limit, @Param("offset") int offset);

    List<UPCModel> getAllComment();
}
