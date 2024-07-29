package com.leepsmart.code.common.utils.returnbody;

/**
 * 状态码
 *
 * @author leepsmart
 */
public enum Code {

    /**
     * 操作成功
     */
    SUCCESS(0),
    /**
     * 操作失败
     */
    ERROR(1),
    /**
     * 系统异常
     */
    SYS_EXCEPTION(2),
    /**
     * 业务异常
     */
    SERVICE_EXCEPTION(3),
    /**
     * 参数错误
     */
    PARAM_ERROR(4),
    /**
     * 无权操作
     */
    NO_AUTHORITY_OPERATE(5),
    /**
     * 请求类型错误
     */
    REQUEST_TYPE_ERROR(6),
    /**
     * 身份校验失败
     */
    IDENTITY_VERIFY_ERROR(7),
    /**
     * 文件上传失败
     */
    IO_UPLOAD_ERROR(10),
    /**
     * 文件删除失败
     */
    IO_DELETE_ERROR(11),
    /**
     * 图片验证码错误
     */
    IMAGE_CODE_ERROR(12),
    /**
     * 短信验证码错误
     */
    SMS_CODE_ERROR(13),
    /**
     * 用户名或者密码错误
     */
    ACCOUNT_OR_PWD_ERROR(14),
    /**
     * jwt 秘钥到期
     */
    JWT_EXPIRE(15),
    /**
     * 分页参数错误
     */
    PAGE_ERROR(16),
    /**
     * VIP特权
     */
    IS_TRIAL_MEMBER(17),
    /**
     * 账户余额不足
     */
    BALANCE_NOT_ENOUGH(1000),
    /**
     * 充值金额最小值错误
     */
    RECHARGE_MIN_ERROR(1001),
    /**
     * 订单可用延迟次数已达到限制
     */
    ORDER_DELAY_ERROR(1002);

    private Integer code;

    Code(Integer code) {
        this.code = code;
    }

    public Integer getValue() {
        return code;
    }

}
