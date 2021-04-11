package com.ITIS.DreamTreeSharer.dao;

import com.ITIS.DreamTreeSharer.entity.UsersEntity;
import com.ITIS.DreamTreeSharer.model.UserModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @ClassName LoginDao
 * @Author LCX
 * @Date 2021 2021-03-26 9:54 p.m.
 * @Version 1.0
 **/
@Repository
public interface LoginDao extends BaseMapper<UsersEntity> {
}
