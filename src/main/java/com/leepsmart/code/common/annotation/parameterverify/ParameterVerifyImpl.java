package com.leepsmart.code.common.annotation.parameterverify;

import com.leepsmart.code.common.ex.ServiceException;
import com.leepsmart.code.common.utils.returnbody.ResultCodeInfo;
import com.leepsmart.code.common.utils.returnbody.ReturnBody;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 参数校验注解实现: 切面
 *
 * @author leepsmart
 */
@Aspect
@Component
public class ParameterVerifyImpl {
    private Pattern verifyPhone = Pattern.compile("^1[2-9]\\d{9}$");

    private Pattern verifyIdCard = Pattern.compile("(^\\d{8}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}$)|(^\\d{6}(18|19|20)\\d{2}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}(\\d|X|x)$)");

    /**
     * 定义执行该 aop 的条件 条件为被 ParameterVerify 注解下的方法
     */
    @Pointcut("@annotation(com.leepsmart.code.common.annotation.parameterverify.ParameterVerify)")
    private void addAdvice() {
    }

    /**
     * 环绕切面
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("addAdvice()")
    private Object interceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        ParameterVerify annotation = sign.getMethod().getAnnotation(ParameterVerify.class);
        Map<String, Object> params = handlerParameter(joinPoint);
        // 非空验证
        if (!verifyNull(sign.getMethod(), annotation, params)) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }

        // 手机号码验证
        if (!verifyPhone(sign.getMethod(), annotation, params)) {
            return ReturnBody.error("手机号码校验错误");
        }

        // 身份证号检验(第一代/第二代)
        if (!verifyIdCard(sign.getMethod(), annotation, params)) {
            return ReturnBody.error("身份证号码校验错误");
        }

        // 字段长度校验
        if (!verifyLength(sign.getMethod(), annotation, params)) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }

        // 执行方法并返回结果
        return joinPoint.proceed();
    }

    /**
     * 验证字段长度
     *
     * @param method     所属方法
     * @param annotation 注解
     * @param params     参数集
     * @return
     */
    private boolean verifyLength(Method method, ParameterVerify annotation, Map<String, Object> params) {
        for (Length length : annotation.length()) {
            if (!"".equals(length.name())) {
                Object param = params.get(length.name());
                if (null != param && !"".equals(param)) {
                    int paramLength;
                    if (param instanceof MultipartFile) {
                        paramLength = BigDecimal.valueOf(((MultipartFile) param).getSize()).divide(BigDecimal.valueOf(1000), BigDecimal.ROUND_HALF_UP).intValue();
                    } else if (param instanceof File) {
                        paramLength = BigDecimal.valueOf(((File) param).length()).divide(BigDecimal.valueOf(1000), BigDecimal.ROUND_HALF_UP).intValue();
                    } else if (param instanceof String) {
                        paramLength = param.toString().length();
                    } else {
                        paramLength = new BigDecimal(param.toString()).intValue();
                    }
                    if (length.min() > 0) {
                        if (paramLength < length.min()) {
                            System.err.println(MessageFormat.format("methods: {0} 定义的参数 {1} 长度（大小）为 {2} 小于限制的最小长度（大小） {3} ", method, param, paramLength, length.min()));
                            return false;
                        }
                    }
                    if (length.max() > 0) {
                        if (paramLength > length.max()) {
                            System.err.println(MessageFormat.format("methods: {0} 定义的参数 {1} 长度（大小）为 {2} 大于限制的最大长度（大小） {3} ", method, param, paramLength, length.max()));
                            return false;
                        }
                    }
                    if (length.limit() > 0) {
                        if (length.limit() != paramLength) {
                            System.err.println(MessageFormat.format("methods: {0} 定义的参数 {1} 长度（大小）为 {2} 不等于限制的长度（大小） {3} ", method, param, paramLength, length.limit()));
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * 非空校验
     *
     * @param method     所属方法
     * @param annotation 注解
     * @param params     参数集
     * @return
     */
    private boolean verifyNull(Method method, ParameterVerify annotation, Map<String, Object> params) {
        if (null != annotation.notNull()[0] && !"".equals(annotation.notNull()[0])) {
            for (String notNull : annotation.notNull()) {
                Object param = params.get(notNull);
                if (null == param || "".equals(param)) {
                    System.err.println(MessageFormat.format("methods: {0} 定义的参数 {1} 值为NULL", method, notNull));
                    throw new ServiceException(String.format("参数%s不能为空",notNull));
                }
            }
        }
        return true;
    }

    /**
     * 校验手机号码
     *
     * @param method     所属方法
     * @param annotation 注解
     * @param params     参数集
     * @return
     */
    private boolean verifyPhone(Method method, ParameterVerify annotation, Map<String, Object> params) {
        if (null != annotation.isPhone()[0] && !"".equals(annotation.isPhone()[0])) {
            for (String phone : annotation.isPhone()) {
                Object phoneVal = params.get(phone);
                if (null != phoneVal && !"".equals(phoneVal)) {
                    if (!verifyPhone.matcher(phoneVal.toString()).matches()) {
                        System.err.println(MessageFormat.format("methods: {0} 定义的参数 {1} 手机号码校验错误", method, phone));
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 检验身份证号码
     *
     * @param method     所属方法
     * @param annotation 注解
     * @param params     参数集
     * @return
     */
    private boolean verifyIdCard(Method method, ParameterVerify annotation, Map<String, Object> params) {
        if (null != annotation.isIdCard()[0] && !"".equals(annotation.isIdCard()[0])) {
            for (String idCard : annotation.isIdCard()) {
                Object idCardVal = params.get(idCard);
                if (null != idCardVal && !"".equals(idCardVal)) {
                    if (!verifyIdCard.matcher(idCardVal.toString()).matches()) {
                        System.err.println(MessageFormat.format("methods: {0} 定义的参数 {1} 身份证号码校验错误", method, idCard));
                        return false;
                    }
                }
            }
        }
        return true;
    }


    /**
     * 获取参数名以及参数值
     * 获取对象使用java反射机制
     */
    private Map<String, Object> handlerParameter(ProceedingJoinPoint point) throws Exception {
        Map<String, Object> params = new HashMap<>();
        String[] parameterNames = ((MethodSignature) point.getSignature()).getParameterNames();
        Object[] args = point.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (verifyType(args[i])) {
                params.put(parameterNames[i], args[i]);
            } else if (args[i] != null) {
                for (Field fieldItem : args[i].getClass().getDeclaredFields()) {
                    fieldItem.setAccessible(true);
                    params.put(parameterNames[i] + "." + fieldItem.getName(), fieldItem.get(args[i]));
                }
            } else {
                params.put(parameterNames[i], args[i]);
            }
        }
        return params;
    }

    private Boolean verifyType(Object obj) {
        return obj instanceof String
                || obj instanceof Integer
                || obj instanceof Long
                || obj instanceof Double
                || obj instanceof BigDecimal
                || obj instanceof File
                || obj instanceof MultipartFile;
    }
}
