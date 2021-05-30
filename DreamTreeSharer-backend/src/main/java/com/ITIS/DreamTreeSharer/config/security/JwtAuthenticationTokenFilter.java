package com.ITIS.DreamTreeSharer.config.security;

import com.ITIS.DreamTreeSharer.utils.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName JwtAuthenticationTokenFilter
 * @Author LCX
 * @Date 2021 2021-03-27 8:52 a.m.
 * @Version 1.0
 * 对请求进行拦截后校验请求头里面的 token header 是否有效，有效则放行，否则拦截
 **/
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader; //Authorization
    @Value("${jwt.tokenHead}")
    private String tokenHead; //ITIS
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(tokenHeader);
        //存在 token
        if(null != authHeader && authHeader.startsWith(tokenHead)) {
            String authToken = authHeader.substring(tokenHead.length());
            String username = null;
            try {
                username = jwtTokenUtil.getUserNameFromToken(authToken);
            }catch (ExpiredJwtException e) {
                System.out.println(e.getMessage()+"--用户未登录或者登录有效期已过！");
            }
            //token 存在用户名但未登录
            if(null != username && null == SecurityContextHolder.getContext().getAuthentication()) {
                //登录
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                //验证 token 是否有效，重新设置用户对象
                if(jwtTokenUtil.validateToken(authToken,userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        // 放行请求
        filterChain.doFilter(request,response);
    }
}
