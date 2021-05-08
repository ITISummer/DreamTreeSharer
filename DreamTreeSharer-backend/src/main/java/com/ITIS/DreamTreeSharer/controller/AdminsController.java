package com.ITIS.DreamTreeSharer.controller;


import com.ITIS.DreamTreeSharer.config.common.Message;
import com.ITIS.DreamTreeSharer.config.common.StatusCode;
import com.ITIS.DreamTreeSharer.entity.UsersEntity;
import com.ITIS.DreamTreeSharer.model.CRModel;
import com.ITIS.DreamTreeSharer.service.AdminsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author SummerLv
 * @since 2021-03-26
 */
@RestController
//@RequestMapping("/admins")
@Api(tags = "AdminController")
public class AdminsController {

}
