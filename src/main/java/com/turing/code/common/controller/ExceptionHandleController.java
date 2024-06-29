package com.turing.code.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.turing.code.common.ex.ServiceException;
import com.turing.code.common.utils.CommUtil;
import com.turing.code.common.utils.LogUtil;
import com.turing.code.common.utils.returnbody.Code;
import com.turing.code.common.utils.returnbody.ResultCodeInfo;
import com.turing.code.common.utils.returnbody.ReturnBody;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@ControllerAdvice
public class ExceptionHandleController {

    @Resource
    HttpServletRequest request;

    @ExceptionHandler(BadCredentialsException.class)
    public String badCredentialsException() {
        return ReturnBody.error(ResultCodeInfo.ACCOUNT_OR_PWD_ERROR);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public String usernameNotFoundException() {
        return ReturnBody.error(ResultCodeInfo.IDENTITY_VERIFY_ERROR);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String noHandlerFoundException() {
        return ReturnBody.error(ResultCodeInfo.REQUEST_TYPE_ERROR);
    }

    @ExceptionHandler(ServiceException.class)
    public String serviceException(ServiceException e) {
        LogUtil.info("业务模块异常: " + e.getMessage());
        if (null != request) {
            LogUtil.info("request: " + request.getServletPath());
        }
        e.printStackTrace();
        String message = e.getMessage();
        if (CommUtil.checkNull(message)) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(message);
                return ReturnBody.error(jsonObject.getInteger("code"), jsonObject.getString("result"));
            } catch (Exception ex) {
                return ReturnBody.error(Code.SYS_EXCEPTION, message);
            }
        } else {
            return ReturnBody.error(ResultCodeInfo.SYS_EXCEPTION);
        }
    }


    @ExceptionHandler(Exception.class)
    public String allException(Exception e) {
        e.printStackTrace();
        return ReturnBody.error(ResultCodeInfo.SYS_EXCEPTION);
    }

}
