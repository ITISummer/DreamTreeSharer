package com.ITIS.DreamTreeSharer.config.security;

import com.ITIS.DreamTreeSharer.config.common.Message;
import com.ITIS.DreamTreeSharer.config.common.StatusCode;
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
 * @create: 2021/2/27 23:34
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        CRModel model = CRModel.error(StatusCode.PERMISSION,Message.ERR_NO_PERMISSION);
        out.write(new ObjectMapper().writeValueAsString(model));
        out.flush();
        out.close();
    }
}
