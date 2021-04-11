package com.ITIS.DreamTreeSharer.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName JwtTokenUtil
 * @Author LCX
 * @Date 2021 2021-03-26 6:00 p.m.
 * @Version 1.0
 * 加密过程：
 * 从 UserDetails 中获取用户名 username 后结合密钥 secret 使用 HS512 加密算法进行加密
 * 期间有对 token 设置创建时间，有效期及失效时间
 *
 * 解密过程：
 * 先解密 token，获取设置的荷载后，从其中获取用户名 username
 *
 * 另外有判断 token 是否有效的方法：
 * 1.判断token中username是否等于UserDetails中的 username
 * 2.判断token是否已经过期
 * [JSON Web Token 入门教程](https://www.ruanyifeng.com/blog/2018/07/json_web_token-tutorial.html)
 **/
@Component
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME = "sub";  //荷载用户
    private static final String CLAIM_KEY_CREATED = "CreateTime"; //创建时间

    @Value("${jwt.secret}")
    private String secret; //密钥
    @Value("${jwt.expiration}")
    private Long expiration;  //token 失效时间

//      private String secret = "ITIS.com";
//      private long expiration = 3600;


    /**
     * 根据用户信息生成加密Token - 加密
     * @param userDetails - security 框架中类
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        //持有人为当前登录用户
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        //创建时间为当前时间
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

//    public static void main(String[] args) {
//        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
//        UserDetails userDetails = new UserDetails() {
//            @Override
//            public Collection<? extends GrantedAuthority> getAuthorities() {
//                return null;
//            }
//
//            @Override
//            public String getPassword() {
//                return null;
//            }
//
//            @Override
//            public String getUsername() {
//                return "summer";
//            }
//
//            @Override
//            public boolean isAccountNonExpired() {
//                return false;
//            }
//
//            @Override
//            public boolean isAccountNonLocked() {
//                return false;
//            }
//
//            @Override
//            public boolean isCredentialsNonExpired() {
//                return false;
//            }
//
//            @Override
//            public boolean isEnabled() {
//                return false;
//            }
//        };
//        System.out.println(jwtTokenUtil.generateToken(userDetails));
//    }
    /**
     * 根据荷载生成 JWT Token - 加密
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)  //设置荷载
                .setExpiration(generateExpirationDate())  //设置失效时间
                .signWith(SignatureAlgorithm.HS512, secret) //设置加密算法及密钥
                .compact(); //使用序列化压缩为 url 安全的字符串
    }

    /**
     * 设置token失效时间 - 设置 token 存活时间
     * @return
     */
    private Date generateExpirationDate() {
        //失效时间 =  当前系统的时间 + 配置的有效时间 24h
        return new Date(System.currentTimeMillis() + expiration*1000);
    }

    /**
     * 从token中获取用户名 - 解密
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token) {
        String username;
        //需要先获取荷载，因为用户名存放在荷载中
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            //如果获取用户名出现异常，username = null
            username = null;
        }
        return username;
    }

    /**
     * 从token中获取荷载 - 解密
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 判断token是否有效
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        /**
         * 判断token是否有效主要做两个判断
         * 1.判断token中username是否等于UserDetails中的 username
         * 2.判断token是否已经过期
         */
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否过期
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        /**
         * 1.获取token的过期时间
         * 2.判断token的过期时间是否大于在当前时间的前面
         */
        Date expireDate = getExpiredDateFromToken(token);
        return expireDate.before(new Date());
    }


    /**
     * 从荷载里面获取失效时间
     * @param token
     * @return
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }


    /**
     * 判断token是否可以被刷新
     * @param token
     * @return
     */
    public boolean canRefresh(String token) {
        //如果token已经过期则可以刷新
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        /**
         * 刷新token只需要将token的生成时间改成当前时间，然后再重新生成token即可。
         */
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }
}
