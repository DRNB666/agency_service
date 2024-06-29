package com.turing.code.common.utils.returnbody;

/**
 * 返回信息
 *
 * @author Turing
 */
public class ResultLabel {

    public final static String SUCCESS = "操作成功";

    public final static String ERROR = "操作失败";

    public final static String SYS_EXCEPTION = "系统异常";

    public final static String SERVICE_EXCEPTION = "服务内部错误";

    public final static String PARAM_ERROR = "参数错误";

    public final static String NO_AUTHORITY_OPERATE = "无权操作";

    public final static String REQUEST_TYPE_ERROR = "请求错误";

    public final static String IDENTITY_VERIFY_ERROR = "身份校验失败";

    public final static String IO_UPLOAD_ERROR = "文件上传失败";

    public final static String IO_DELETE_ERROR = "文件删除失败";

    public final static String IMAGE_CODE_ERROR = "图片验证码错误";

    public final static String SMS_CODE_ERROR = "短信验证码错误";

    public final static String PAGE_ERROR = "分页参数有误";

    public final static String ACCOUNT_OR_PWD_ERROR = "用户名或密码错误";

    public final static String JWT_EXPIRE = "登录已失效, 请重新登录";

}
