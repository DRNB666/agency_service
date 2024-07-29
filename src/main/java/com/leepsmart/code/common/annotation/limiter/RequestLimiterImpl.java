package com.leepsmart.code.common.annotation.limiter;

import com.leepsmart.code.common.utils.CommUtil;
import com.leepsmart.code.common.utils.LogUtil;
import com.leepsmart.code.common.utils.returnbody.ReturnBody;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 限流注解实现
 */
@Aspect
@Component
public class RequestLimiterImpl {

    private final Map<String, RateLimiter> rateLimiterMap = new ConcurrentHashMap<>();

    /**
     * 定义切点条件
     */
    @Pointcut("execution(public String com.leepsmart.*.*.controller.Controller.*(..))")
    private void addAdvice() {
    }

    @Around("addAdvice()")
    @SuppressWarnings("all")
    private Object around(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature sign = (MethodSignature) pjp.getSignature();
        // 获取该方法的 RequestLimiter 注解
        RequestLimiter annotation = sign.getMethod().getAnnotation(RequestLimiter.class);
        if (annotation == null) {
            // 如果方法没有，尝试获取类注解
            Class<?> clazz = pjp.getTarget().getClass();
            annotation = clazz.getAnnotation(RequestLimiter.class);
            if (annotation == null) {
                // 如果类也没有注解，直接放行
                return pjp.proceed();
            }
        }
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) Objects.requireNonNull(requestAttributes).resolveReference(RequestAttributes.REFERENCE_REQUEST);
        // 获取请求url
        String url = Objects.requireNonNull(request).getRequestURI();
        String ip = CommUtil.getIpAddr(request);
        RateLimiter rateLimiter;
        if (annotation.ipLimit()) {
            url = ip + url;
        }
        // 判断map集合中是否有创建好的令牌桶
        if (!rateLimiterMap.containsKey(url)) {
            // 创建令牌桶,以n r/s往桶中放入令牌
            rateLimiter = RateLimiter.create(annotation.qps());
            rateLimiterMap.put(url, rateLimiter);
        }
        // 获取令牌
        rateLimiter = rateLimiterMap.get(url);
        boolean acquire = rateLimiter.tryAcquire(annotation.timeout(), annotation.timeunit());
        if (acquire) {
            // 获取令牌成功
            // 执行方法并获取方法结果
            return pjp.proceed();
        } else {
            if (annotation.ipLimit()) {
                LogUtil.warn("请求被限流， ip：{}，url：{}", ip, url);
            } else {
                LogUtil.warn("请求被限流，url：{}", url);
            }
            return ReturnBody.error(annotation.msg());
        }
    }

}
