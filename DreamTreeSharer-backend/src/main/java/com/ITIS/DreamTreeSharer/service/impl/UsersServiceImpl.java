package com.ITIS.DreamTreeSharer.service.impl;

import com.ITIS.DreamTreeSharer.config.common.Message;
import com.ITIS.DreamTreeSharer.config.common.StatusCode;
import com.ITIS.DreamTreeSharer.config.security.JwtTokenUtil;
import com.ITIS.DreamTreeSharer.dao.UsersDao;
import com.ITIS.DreamTreeSharer.entity.UsersEntity;
import com.ITIS.DreamTreeSharer.model.CRModel;
import com.ITIS.DreamTreeSharer.model.UsersModel;
import com.ITIS.DreamTreeSharer.service.UsersService;
import com.ITIS.DreamTreeSharer.utils.EmailUtil;
import com.ITIS.DreamTreeSharer.utils.MD5;
import com.ITIS.DreamTreeSharer.utils.RedisUtil;
import com.ITIS.DreamTreeSharer.utils.UsersUtil;
import com.ITIS.DreamTreeSharer.utils.sendSMS.SmsSDKDemo1;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author SummerLv
 * @since 2021-03-26
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersDao, UsersEntity> implements UsersService {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public CRModel updateAvatar(String newAvatarUrl) {
        if (usersDao.update(null, new UpdateWrapper<UsersEntity>().eq("user_id", UsersUtil.getCurrentUser().getUserId()).set("user_avatar_url", newAvatarUrl)) == 1) {
            return new CRModel(StatusCode.SUCCESS, Message.SUCCESS, null);
        }
        return new CRModel(StatusCode.WARNING, "更新头像" + Message.WARNING, null);
    }

    @Override
    public CRModel updatePwd(String pwd) {
        // [mybatis-plus update 更新操作](https://blog.csdn.net/chenglc1612/article/details/107279142)
        pwd = MD5.md5Encrypt(pwd);
        if (usersDao.update(null, new UpdateWrapper<UsersEntity>().eq("user_id", UsersUtil.getCurrentUser().getUserId()).set("user_password", pwd)) == 1) {
            return new CRModel(StatusCode.SUCCESS, Message.SUCCESS, null);
        }
        return new CRModel(StatusCode.WARNING, "更新密码" + Message.WARNING, null);
    }


    @Override
    public CRModel getCode(String flag, String emailOrMobile) {
        if ("email".equals(flag)) {
            // 发送邮箱验证码
            return getEmailCode(emailOrMobile);
        } else if ("mobile".equals(flag)) {
            // 发送短信验证码
            return getSmsCode(emailOrMobile);
        }
        return new CRModel(StatusCode.ERROR, "服务器内部" + Message.ERROR, null);
    }


    /**
     * 发送邮箱验证码
     * @param email
     * @return
     */
    public CRModel getEmailCode(String email) {
        String code = randomSmsCode();
        if (emailUtil.sendEmailCode(email, code)) {
            return CRModel.success(StatusCode.SUCCESS, Message.SUC_SEND_EMAIL_CODE, code);
        } else {
            return CRModel.success(StatusCode.WARNING, Message.WAR_CODE_IN_VALIDITY, null);
        }
    }

    /**
     * 发送短信验证码
     *
     * @param phone
     * @return
     */
    @Override
    public CRModel getSmsCode(String phone) {
        String code = randomSmsCode();
        redisUtil.setKey(phone, code);
        if (SmsSDKDemo1.sendSms(phone, redisUtil.getValue(phone))) {
            return CRModel.success(StatusCode.SUCCESS, Message.SUC_SEND_SMS, code);
        } else {
            return CRModel.success(StatusCode.ERROR, Message.ERR_SERVER_INTERNAL, null);
        }
    }

    @Override
    public CRModel updateEmailOrMobile(String flag, String emailOrMobile, String code) {
        if (code.equals(redisUtil.getValue(emailOrMobile))) {
            // 更新前验证验证码是否输入正确
            if (usersDao.updateEmailOrMobile(UsersUtil.getCurrentUser().getUserId(), flag, emailOrMobile) == 1) {
                return new CRModel(StatusCode.SUCCESS, "更新用户信息" + Message.SUCCESS, null);
            } else {
                return new CRModel(StatusCode.WARNING, "更新用户信息" + Message.WARNING, null);
            }
        } else {
            return new CRModel(StatusCode.WARNING, "" + Message.WAR_CAPTCHA + "或者验证码失效！", null);
        }
    }


    @Override
    public List<UsersEntity> getUserList(String keywords) {
        return usersDao.getUserList(UsersUtil.getCurrentUser().getUserId(), keywords);
    }

    @Override
    public CRModel usernameExisted(String username) {
        if (getCurrentUserInfo(username) != null) {
            return CRModel.warning(StatusCode.WARNING, Message.WAR_USERNAME_EXISTED, null);
        } else {
            return CRModel.warning(StatusCode.SUCCESS, Message.SUC_USERNAME, null);
        }
    }


    /**
     * 添加一个用户
     *
     * @param usersModel
     * @return
     */
    @Override
    public CRModel addOneUser(UsersModel usersModel) {
        // 验证短信验证码是否输入正确
        if (usersModel.getSmsCode().equals(redisUtil.getValue(usersModel.getPhone()))) {
            UsersEntity usersEntity = new UsersEntity();
            usersEntity.setUserId(UUID.randomUUID() + "");
            usersEntity.setUserUsername(usersModel.getUsername());
            usersEntity.setUserPassword(MD5.md5Encrypt(usersModel.getPassword()));
            usersEntity.setUserPhone(usersModel.getPhone());
            if (usersDao.insert(usersEntity) == 1) {
                return new CRModel(StatusCode.SUCCESS, Message.SUC_REGISTER, null);
            } else {
                return new CRModel(StatusCode.ERROR, Message.ERR_SERVER_INTERNAL, null);
            }
        } else {
            return new CRModel(StatusCode.WARNING, Message.WAR_CAPTCHA, null);
        }
    }

    /**
     * 生成短信验证随机码
     *
     * @return
     */
    private String randomSmsCode() {
        // 100000~999999
        int max = 999999;
        int min = 100000;
        int verifCode = min + (int) (Math.random() * (max - min + 1));
        return verifCode + "";
    }

    /**
     * 登陆后返回 token
     *
     * @param usersModel
     * @param request
     * @return 前端输入用户名和密码，后台查数据库判断用户名和密码是否正确，
     * 如果正确则生成 jwt token 返回给前端，后面的每一次请求都会携带此 jwt token，
     * 在每一次前端发起请求前都会拦截请求进行 token 是否有效的验证；
     * 否则提示继续输入。
     */
    @Override
    public CRModel login(UsersModel usersModel, HttpServletRequest request) {
        //取出放入session的验证码并判断验证码是否正确 - 这里的 captcha 字段要和前端传递的字段一样
        String captchaCode = (String) request.getSession().getAttribute("captcha");
        if (!captchaCode.equalsIgnoreCase(usersModel.getCaptcha())) { //忽略大小写
            return CRModel.warning(StatusCode.WARNING, Message.WAR_CAPTCHA, null);
        }
        //获取登录信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(usersModel.getUsername());
//        if (null == userDetails || !passwordEncoder.matches(password, userDetails.getPassword())) {
        if (null == userDetails || !userDetails.getPassword().equals(MD5.md5Encrypt(usersModel.getPassword()))) {
            return CRModel.warning(StatusCode.WARNING, Message.WAR_LOGIN_INVALID_USERNAME_PASSWORD, null);
        }
        if (!userDetails.isEnabled()) {
            return CRModel.warning(StatusCode.WARNING, Message.WAR_IS_FORBIDDEN, null);
        }
        // 更新 security 登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 登录验证成功则生成 token 并返回给前端
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CRModel.success(StatusCode.SUCCESS, Message.SUCCESS, tokenMap);
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    @Override
    public UsersEntity getCurrentUserInfo(String username) {
        //TODO 判断如果是 admin 用户则向 admins 发查询请求；如果是 user 用户，则向 users 发查询请求
        //TODO 两种方法：通过
        return usersDao.selectOne(new QueryWrapper<UsersEntity>().eq("user_username", username).eq("user_enabled", true));
    }


    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getCurrentUserInfo(username);
    }

}
