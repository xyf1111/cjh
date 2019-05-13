package io.chaoshua.common.resetInterceptor;
import java.lang.annotation.*;

/**
 * 功能描述 防止重复提交标记注解
 * Created by dws on 2019/1/19 0019.
 */
@Target(ElementType.METHOD)  // 作用到方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时有效
@Documented
@Inherited
public @interface LocalLock {
    String key() default "key";
    int expire() default 1;
}
