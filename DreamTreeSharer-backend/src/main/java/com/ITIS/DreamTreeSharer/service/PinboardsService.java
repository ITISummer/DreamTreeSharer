package com.ITIS.DreamTreeSharer.service;

import com.ITIS.DreamTreeSharer.entity.PinboardsEntity;
import com.ITIS.DreamTreeSharer.model.CRModel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 愿望板表 服务类
 * </p>
 *
 * @author SummerLv
 * @since 2021-03-26
 */
public interface PinboardsService extends IService<PinboardsEntity> {

    /**
     * 添加一个 pinboard
     * @param pinboardsEntity
     * @return
     */
    CRModel addOnePinboard(PinboardsEntity pinboardsEntity) throws Exception;

    /**
     * 获取当前登录用户上传的 pinboards
     * @return
     */
    CRModel getPinboards();

    /**
     * 删除一个 pinboard
     * @param pinboardId
     * @return
     */
    CRModel deleteOnePinboardById(String pinboardId);

    /**
     * 获取所有的分享的 pinboard
     * @return
     */
    CRModel getSharablePinboard();

    /**
     * 更新愿望卡点赞数
     * @param pinId
     * @param likeNum
     * @return
     */
    CRModel updateLikeNum(String pinId, int likeNum);
}
