package com.ITIS.DreamTreeSharer.controller;


import com.ITIS.DreamTreeSharer.config.common.Message;
import com.ITIS.DreamTreeSharer.model.CRModel;
import com.ITIS.DreamTreeSharer.utils.QiniuToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UsersController
 * @Author LCX
 * @Date 2021 2021-03-26 9:22 p.m.
 * @Version 1.0
 **/
@RestController
@Api(tags = "UsersController")
//@RequestMapping("/users-entity")
public class UsersController {
//    @Autowired
//    private UsersService usersService;

    @ApiOperation(value = "用户获取文件上传token")
    @GetMapping("/qiniu/uploadToken/{key}")
    public CRModel getQiniuToken(@PathVariable String key) {
        String uploadToken = QiniuToken.getBaseToken(QiniuToken.CLOUD_BUCKET,key);
        if (uploadToken == null){
            return CRModel.error(Message.ERR_WRONG_KEY);
        } else {
            return CRModel.success(Message.SUCCESS,uploadToken);
        }
    }
}
