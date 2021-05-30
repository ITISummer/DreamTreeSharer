package com.ITIS.DreamTreeSharer.config.security;

import com.ITIS.DreamTreeSharer.config.constants.Message;
import com.ITIS.DreamTreeSharer.config.constants.StatusCode;
import com.ITIS.DreamTreeSharer.model.CRModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author SummerLv
 * @program: DreamTreeSharer
 * @description: 当未登陆或者token失效访问接口时，自定义返回结果
 * @create: 2021/2/27 23:24
 */
@Component
public class RestfulAuthorizationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        CRModel model = CRModel.warning(StatusCode.WAR_USER_NOT_LOGIN, Message.WAR_NOT_LOGIN, null);
        out.write(new ObjectMapper().writeValueAsString(model));
        out.flush();
        out.close();
    }
}
