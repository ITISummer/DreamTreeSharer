package com.ITIS.DreamTreeSharer.service.impl;

import com.ITIS.DreamTreeSharer.config.constants.Message;
import com.ITIS.DreamTreeSharer.config.constants.StatusCode;
import com.ITIS.DreamTreeSharer.dao.PinboardsDao;
import com.ITIS.DreamTreeSharer.dao.UsersDao;
import com.ITIS.DreamTreeSharer.entity.PinboardsEntity;
import com.ITIS.DreamTreeSharer.entity.UsersEntity;
import com.ITIS.DreamTreeSharer.model.CRModel;
import com.ITIS.DreamTreeSharer.model.UsersModel;
import com.ITIS.DreamTreeSharer.service.UsersService;
import com.ITIS.DreamTreeSharer.utils.*;
import com.ITIS.DreamTreeSharer.utils.sendSMS.SmsSDKDemo1;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    @Autowired
    private PinboardsDao pinsDao;

    private final static String EMAIL = "email";
    private final static String MOBILE = "mobile";
//    [java 泛型详解-绝对是对泛型方法讲解最详细的，没有之一](https://blog.csdn.net/qq_24084925/article/details/68491132)
//    [【工作记录】java方法返回多个值（用法思考、比较）](https://blog.csdn.net/zzh920625/article/details/80462379)
//    本来想使用泛型来简化开发的，但是越弄越复杂，不过还是学到了东西，以前从没有写过泛型！
    /*
    private <T> Map<String,Object> initSearch(T t) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        IPage<T> page = new Page<>();
        Map<String,Object> result =  new HashMap<String,Object>();
        result.put("wrapper",wrapper);
        result.put("page",page);
        return result;
    }
    */

    /*
    [mybatis-plus模糊查询](https://www.jianshu.com/p/36039f2ef4d6)
    [条件构造器](https://mp.baomidou.com/guide/wrapper.html#abstractwrapper)
    [MyBatisPlus之模糊查询加分页和条件构造器](https://blog.csdn.net/qq_43048586/article/details/90025128)
    [Mybatis-plus 常用的方法（包含分页查询，模糊查询，排序……）](https://www.codenong.com/cs106124199/)
     */
    @Override
    public CRModel fuzzySearch(String flag, String search, int size, int currentPage) {
        //Map<String,Object> result = initSearch(UsersEntity.class);
        //QueryWrapper<UsersEntity> wrapper = (QueryWrapper<UsersEntity>) result.get("wrapper");
        switch (flag) {
            //根据用户名进行模糊分页查询
            case "1": {
                QueryWrapper<UsersEntity> wrapper = new QueryWrapper<>();
                wrapper.like("user_username", search);
                long count = usersDao.selectCount(wrapper);
                IPage<UsersEntity> page = new Page<>(currentPage, size, count);
                page.setTotal(count);
                return new CRModel(StatusCode.SUCCESS, "", usersDao.selectPage(page, wrapper));
            }
            //根据pin类型进行模糊分页查询
            case "2": {
                QueryWrapper<PinboardsEntity> wrapper = new QueryWrapper<>();
                wrapper.like("pinboard_type", search);
                long count = pinsDao.selectCount(wrapper);
                IPage<PinboardsEntity> page = new Page<>(currentPage, size, count);
                page.setTotal(count);
                return new CRModel(StatusCode.SUCCESS, "", pinsDao.selectPage(page, wrapper));
            }
            //根据pin标题进行模糊分页查询
            case "3": {
                QueryWrapper<PinboardsEntity> wrapper = new QueryWrapper<>();
                wrapper.like("pinboard_title", search);
                long count = pinsDao.selectCount(wrapper);
                IPage<PinboardsEntity> page = new Page<>(currentPage, size, count);
                page.setTotal(count);
                return new CRModel(StatusCode.SUCCESS, "", pinsDao.selectPage(page, wrapper));
            }
        }
        return new CRModel(StatusCode.WARNING, "搜索" + Message.WARNING, null);
    }


    @Override
    public CRModel updateAvatar(String newAvatarUrl) {
        String userId = UsersUtil.getCurrentUser().getUserId();
        if (usersDao.update(null, new UpdateWrapper<UsersEntity>().eq("user_id", userId).set("user_avatar_url", newAvatarUrl)) == 1) {
            return new CRModel(StatusCode.SUCCESS, "更新头像" + Message.SUCCESS, null);
        }
        return new CRModel(StatusCode.WARNING, "更新头像" + Message.WARNING, null);
    }

    /**
     * [mybatis-plus update 更新操作]
     * (https://blog.csdn.net/chenglc1612/article/details/107279142)
     */
    @Override
    public CRModel updatePwd(String pwd) {
        pwd = MD5.md5Encrypt(pwd);
        if (usersDao.update(null, new UpdateWrapper<UsersEntity>().eq("user_id", UsersUtil.getCurrentUser().getUserId()).set("user_password", pwd)) == 1) {
            return new CRModel(StatusCode.SUCCESS, "更新密码" + Message.SUCCESS, null);
        }
        return new CRModel(StatusCode.WARNING, "更新密码" + Message.WARNING, null);
    }


    @Override
    public CRModel getCode(String flag, String emailOrMobile) {
        if (EMAIL.equals(flag)) {
            // 发送邮箱验证码
            return getEmailCode(emailOrMobile);
        } else if (MOBILE.equals(flag)) {
            // 发送短信验证码
            return getSmsCode(emailOrMobile);
        }
        return new CRModel(StatusCode.ERROR, "服务器内部" + Message.ERROR, null);
    }

    /**
     * 发送邮箱验证码
     */
    public CRModel getEmailCode(String email) {
        // 校验邮箱是否已被注册
        if (usersDao.selectCount(new QueryWrapper<UsersEntity>().eq("user_email", email)) > 0) {
            return CRModel.success(StatusCode.WARNING, "邮箱已被使用！", null);
        }
        String code = randomSmsCode();
        if (emailUtil.sendSimpleEmail(email, code)) {
            return CRModel.success(StatusCode.SUCCESS, Message.SUC_SEND_EMAIL_CODE, code);
        } else {
            return CRModel.success(StatusCode.WARNING, Message.WAR_CODE_IN_VALIDITY, null);
        }
    }

    /**
     * 发送短信验证码
     */
    @Override
    public CRModel getSmsCode(String phone) {
        // 校验手机是否已被注册
        if (usersDao.selectCount(new QueryWrapper<UsersEntity>().eq("user_phone", phone)) > 0) {
            return CRModel.success(StatusCode.WARNING, "手机号已被使用！", null);
        }
        String code = randomSmsCode();
        redisUtil.setKey(phone, code, 60);
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
    public CRModel getUserList(String keywords) {
        String userId = UsersUtil.getCurrentUser().getUserId();
        List<UsersEntity> users = usersDao.getUserList(userId, keywords);
        if (users != null) {
            return new CRModel(StatusCode.SUCCESS, "", users);
        } else {
            return new CRModel(StatusCode.WARNING, Message.WAR_NOT_FOUND_USER, null);
        }
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
     * 前端输入用户名和密码，后台查数据库判断用户名和密码是否正确，
     * 如果正确则生成 jwt token 返回给前端，后面的每一次请求都会携带此 jwt token，
     * 在每一次前端发起请求前都会拦截请求进行 token 是否有效的验证；
     * 否则提示继续输入。
     */
    @Override
    public CRModel login(UsersModel usersModel, HttpServletRequest request) {
        //取出放入session的验证码并判断验证码是否正确 - 这里的 captcha 字段要和前端传递的字段一样
        String captchaCode = (String) request.getSession().getAttribute("captcha");
//        String captchaCode = redisUtil.getValue(request.getSession().getId());
        if (captchaCode == null) {
            return CRModel.warning(StatusCode.WARNING, "请重新刷新生成验证码！", null);
        }
        if (!captchaCode.equalsIgnoreCase(usersModel.getCaptcha())) { //忽略大小写
            return CRModel.warning(StatusCode.WARNING, Message.WAR_CAPTCHA, null);
        }
        //获取登录信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(usersModel.getUsername());
        if (null == userDetails || !userDetails.isEnabled()) {
            return CRModel.warning(StatusCode.WARNING, Message.WAR_IS_FORBIDDEN, null);
        }
//        if (null == userDetails || !passwordEncoder.matches(password, userDetails.getPassword())) {
        if ( !userDetails.getPassword().equals(MD5.md5Encrypt(usersModel.getPassword()))) {
            return CRModel.warning(StatusCode.WARNING, Message.WAR_LOGIN_INVALID_USERNAME_PASSWORD, null);
        }
        // 更新 security 登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 登录验证成功则生成 token 并返回给前端
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CRModel.success(StatusCode.SUCCESS, "登录"+Message.SUCCESS, tokenMap);
    }

    /**
     * 根据用户名获取用户信息
     */
    @Override
    public UsersEntity getCurrentUserInfo(String username) {
        //TODO 判断如果是 admin 用户则向 admins 发查询请求；如果是 user 用户，则向 users 发查询请求
        //TODO 两种方法：通过
        return usersDao.selectOne(new QueryWrapper<UsersEntity>().eq("user_username", username).eq("user_enabled", true));
    }


    /**
     * 根据用户名获取用户信息
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getCurrentUserInfo(username);
    }

}
