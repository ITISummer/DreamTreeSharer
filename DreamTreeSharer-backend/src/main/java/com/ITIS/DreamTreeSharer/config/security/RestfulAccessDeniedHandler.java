package com.ITIS.DreamTreeSharer.config.security;

import com.ITIS.DreamTreeSharer.config.constants.Message;
import com.ITIS.DreamTreeSharer.config.constants.StatusCode;
import com.ITIS.DreamTreeSharer.model.CRModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author SummerLv
 * @program: DreamTreeSharer
 * @description: 访问接口没有权限时，自定义返回结果
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        CRModel model = CRModel.warning(StatusCode.WAR_USER_NO_PERMISSION,Message.WAR_NO_PERMISSION,null);
        out.write(new ObjectMapper().writeValueAsString(model));
        out.flush();
        out.close();
    }
}
