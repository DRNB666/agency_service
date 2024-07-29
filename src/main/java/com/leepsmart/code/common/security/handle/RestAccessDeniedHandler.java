package com.leepsmart.code.common.security.handle;

import com.leepsmart.code.common.utils.returnbody.ResultCodeInfo;
import com.leepsmart.code.common.utils.returnbody.ReturnBody;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定403返回值
 *
 * @author leepsmart
 * Created on 2017/12/8
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(403);
        response.getWriter().write(ReturnBody.error(ResultCodeInfo.NO_AUTHORITY_OPERATE));
    }

}
