package com.ITIS.DreamTreeSharer.service;

import com.ITIS.DreamTreeSharer.entity.AdminsEntity;
import com.ITIS.DreamTreeSharer.model.CRModel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 管理员表 服务类
 * </p>
 *
 * @author SummerLv
 * @since 2021-03-26
 */
public interface AdminsService extends IService<AdminsEntity> {
    /** 得到所有用户 */
    CRModel getAllUser(int currentPage,int size);

    /** 得到所有 pin 以及创建者信息 */
    CRModel getAllPin(int currentPage,int size);

    /** 得到所有 comment 以及提交 comment 的用户信息 */
    CRModel getAllComment(int currentPage,int size);

    /** 禁用一个用户 */
    CRModel disableAUser(String userId,boolean enable);

    /** 删除一个 pinboard */
    CRModel deleteAPin(String pinId);

    /** 删除一条评论 */
    CRModel deleteAComment(int commentId);
}
