package com.turing.code.common.security.handle;

import com.turing.code.common.utils.returnbody.ResultCodeInfo;
import com.turing.code.common.utils.returnbody.ReturnBody;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定401返回值
 *
 * @author Turing
 * Created on 2017/12/8
 */
@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (response.getStatus() != 401) {
            response.setStatus(401);
            String result = "";
            if (authException instanceof BadCredentialsException) { /**身份认证未通过*/
                result = ReturnBody.error(ResultCodeInfo.NO_AUTHORITY_OPERATE);
            } else {
                result = ReturnBody.error(ResultCodeInfo.NO_AUTHORITY_OPERATE);
            }
            response.getWriter().write(result);
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
    }

}
