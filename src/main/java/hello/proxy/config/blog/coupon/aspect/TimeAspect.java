package hello.proxy.config.blog.coupon.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class TimeAspect {

    @Around("execution(* hello.proxy.blog.*Controller.*(..))")
    public Object timeLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        log.info("CouponController start - by proxy");

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();
        log.info("CouponController end - by proxy => {}ms", end - start);

        return result;
    }
}
