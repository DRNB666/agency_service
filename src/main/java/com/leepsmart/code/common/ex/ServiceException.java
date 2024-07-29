package com.leepsmart.code.common.ex;

/**
 * 自定义业务异常模块
 */
public class ServiceException extends RuntimeException {

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

}
