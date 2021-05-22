package com.ITIS.DreamTreeSharer.config.security;

import com.ITIS.DreamTreeSharer.entity.UsersEntity;
import com.ITIS.DreamTreeSharer.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @ClassName SecurityConfig
 * @Author LCX
 * @Date 2021 2021-03-26 10:20 p.m.
 * @Version 1.0
 * <p>
 * 前端输入用户名和密码，后台查数据库判断用户名和密码是否正确，
 * 如果正确则生成 jwt token 返回给前端，后面的每一次请求都会携带此 jwt token，
 * 在每一次前端发起请求前都会拦截请求进行 token 是否有效的验证；
 * 否则提示继续输入。
 **/
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UsersService usersService;
    @Autowired
    private RestfulAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private RestfulAuthorizationEntryPoint restfulAuthorizationEntryPoint;

    /**
     * 该方法用于配置security在启动时使用我们重写的userDetailsService
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
//      auth.userDetailsService(userDetailsServiceForAdmin()).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        // [Java Lambda 表达式](https://www.runoob.com/java/java8-lambda-expressions.html)
        return username -> {
            UsersEntity usersEntity = usersService.getCurrentUserInfo(username);
            if (null != usersEntity) {
                return usersEntity;
            }
            return null;
        };
    }

//    @Override
//    @Bean
//    public UserDetailsService userDetailsServiceForAdmin() {
//        return username -> {
//            AdminsEntity adminsEntity = adminsService.getCurrentUserInfo(username);
//            if (null != adminsEntity) {
//                return adminsEntity;
//            }
//            return null;
//        };
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置security
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                //使用JWT.不需要csrf
                .disable()
                //基于 token，不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //所有请求都需要认证
                //以下放行请求可以参考 https://blog.csdn.net/u012702547/article/details/106395776
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/get-sms-code/**",
                        "/username-existed/**",
                        "/qiniu/uploadToken/**",
                        "/register",
                        "/login")
                .permitAll()
                //所有请求都需要认证
                .anyRequest()
                .authenticated()
                //禁用缓存
                .and()
                .headers()
                .cacheControl();

        //添加jwt 登陆授权过滤器，这样每次请求都会检验 jwt token 是否有效
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //添加自定义未授权和未登录结果返回
        http.exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler) //权限不足 - 403
                .authenticationEntryPoint(restfulAuthorizationEntryPoint); //未登录 - 401

    }

    /**
     * 放行的静态资源请求可以全部放这里
     * 不走拦截链
     * [Ant 风格路径表达式](https://www.jianshu.com/p/189847a7d1c7)
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/css/**",
                "/js/**",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/doc.html",
                "/ws/**",
                "/favicon.icon",
                "/logout",
                "/captcha",
                "/get-all-user",
                "/get-all-pin",
                "/get-all-comment"
        );
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }


    /**
     * 跨域配置
     * [Springboot 2.4.0跨域配置无效及接口访问报错（解决方法）allowedOrigins cannot contain the special value "*"]
     * (https://www.cnblogs.com/technicist/p/14466665.html)
     *
     * [SpringBoot 优雅解决 ajax 跨域请求](https://juejin.cn/post/6844903954015322126)
     */

}
