package com.leepsmart.code.common.utils.returnbody;

/**
 * 状态码与返回消息
 *
 * @author leepsmart
 */
public enum ResultCodeInfo {
    /**
     * 成功
     */
    SUCCESS(Code.SUCCESS, ResultLabel.SUCCESS),
    /**
     * 失败
     */
    ERROR(Code.ERROR, ResultLabel.ERROR),
    /**
     * 系统异常
     */
    SYS_EXCEPTION(Code.SYS_EXCEPTION, ResultLabel.SYS_EXCEPTION),
    /**
     * 服务异常
     */
    SERVICE_EXCEPTION(Code.SERVICE_EXCEPTION, ResultLabel.SERVICE_EXCEPTION),
    /**
     * 参数错误
     */
    PARAM_ERROR(Code.PARAM_ERROR, ResultLabel.PARAM_ERROR),
    /**
     * 无权操作
     */
    NO_AUTHORITY_OPERATE(Code.NO_AUTHORITY_OPERATE, ResultLabel.NO_AUTHORITY_OPERATE),
    /**
     * 请求错误
     */
    REQUEST_TYPE_ERROR(Code.REQUEST_TYPE_ERROR, ResultLabel.REQUEST_TYPE_ERROR),
    /**
     * 身份校验失败
     */
    IDENTITY_VERIFY_ERROR(Code.IDENTITY_VERIFY_ERROR, ResultLabel.IDENTITY_VERIFY_ERROR),
    /**
     * 文件上传失败
     */
    IO_UPLOAD_ERROR(Code.IO_UPLOAD_ERROR, ResultLabel.IO_UPLOAD_ERROR),
    /**
     * 文件删除失败
     */
    IO_DELETE_ERROR(Code.IO_DELETE_ERROR, ResultLabel.IO_DELETE_ERROR),
    /**
     * 图片验证码错误
     */
    IMAGE_CODE_ERROR(Code.IMAGE_CODE_ERROR, ResultLabel.IMAGE_CODE_ERROR),
    /**
     * 短信验证码错误
     */
    SMS_CODE_ERROR(Code.SMS_CODE_ERROR, ResultLabel.SMS_CODE_ERROR),
    /**
     * 分页参数错误
     */
    PAGE_ERROR(Code.JWT_EXPIRE, ResultLabel.PAGE_ERROR),
    /**
     * 账号或密码错误
     */
    ACCOUNT_OR_PWD_ERROR(Code.ACCOUNT_OR_PWD_ERROR, ResultLabel.ACCOUNT_OR_PWD_ERROR),
    /**
     * 登录失效
     */
    JWT_EXPIRE(Code.JWT_EXPIRE, ResultLabel.JWT_EXPIRE),


    // 业务错误码
    BALANCE_NOT_ENOUGH(Code.BALANCE_NOT_ENOUGH, "账户余额不足"),
    ;

    private Integer code;

    private String result;

    ResultCodeInfo(Code code, String result) {
        this.code = code.getValue();
        this.result = result;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getResult() {
        return this.result;
    }
}
