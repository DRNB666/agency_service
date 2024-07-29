package com.leepsmart.code.common.annotation.repeat;

import com.alibaba.fastjson2.JSONObject;
import com.leepsmart.code.common.utils.returnbody.ReturnBody;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author base
 */
@Aspect
@Component
public class PreventRepeatImpl {

    private static final Set<String> KEY = new ConcurrentSkipListSet<>();

    private static final ReentrantLock LOCK = new ReentrantLock(true);

    @Pointcut("@annotation(com.leepsmart.code.common.annotation.repeat.PreventRepeat)")
    public void preventRepeat() {
    }

    /**
     * 对方法拦截后进行参数验证
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("preventRepeat()")
    public Object duplicate(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature msig = (MethodSignature) pjp.getSignature();
        Method currentMethod = pjp.getTarget().getClass().getMethod(msig.getName(), msig.getParameterTypes());
        //拼接签名
        StringBuilder sb = new StringBuilder(currentMethod.toString());
        Object[] args = pjp.getArgs();
        for (Object object : args) {
            if (object != null) {
                sb.append(object.getClass().toString());
                try {
                    sb.append(JSONObject.toJSONString(object));
                } catch (Exception e) {
                    sb.append(object.toString());
                }
            }
        }
        String sign = sb.toString();
        synchronized (KEY) {
            boolean success = KEY.add(sign);
            if (!success) {
                return ReturnBody.error("请求过于频繁，请稍后再试");
            }
        }
        Object returnValue;
        try {
            returnValue = pjp.proceed();
        } finally {
            KEY.remove(sign);
        }
        return returnValue;
    }
}
