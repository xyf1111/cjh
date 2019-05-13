package io.chaoshua.common.resetInterceptor;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.chaoshua.common.exception.RRException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2019/1/19 0019.
 */
@Aspect
@Configuration
public class LockMethodInterceptor {
    private static final Cache<String, Object> CACHES = CacheBuilder.newBuilder()
            // 最大缓存 100 个
            .maximumSize(1000)
            // 设置写缓存后 2 秒钟过期
            .expireAfterWrite(2, TimeUnit.SECONDS)
            .build();

    @Around("execution(public * *(..)) && @annotation(io.chaoshua.common.resetInterceptor.LocalLock)")
    public Object interceptor(ProceedingJoinPoint pjp) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
//       MethodSignature signature = (MethodSignature) pjp.getSignature();
//        Method method = signature.getMethod();
//        LocalLock localLock = method.getAnnotation(LocalLock.class);
        String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
        String key = sessionId + "_"+ request.getServletPath();
        if (!StringUtils.isEmpty(key)) {
            if (CACHES.getIfPresent(key) != null) { // 如果缓存中有这个url视为重复提交
                throw new RRException("请勿重复请求");
            }
            // 如果是第一次请求,就将 key 当前对象压入缓存中
            CACHES.put(key, key);
        }
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new RRException("服务器异常");
        } finally {
            //  CACHES.invalidate(key)清楚缓存
           // CACHES.invalidate(key);
        }
    }

    /**
     *
     * @param keyExpress 表达式
     * @param args       参数
     * @return 生成的key
     */
    private String getKey(String keyExpress, Object[] args) {
        for (int i = 0; i < args.length; i++) {
            keyExpress = keyExpress.replace("arg[" + i + "]", args[i].toString());
        }
        return keyExpress;
    }
}
