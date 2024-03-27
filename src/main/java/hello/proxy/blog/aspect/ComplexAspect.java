package hello.proxy.blog.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class ComplexAspect {

    @Pointcut("execution(* hello.proxy.blog.Coupon..*(..))")
    private void allCoupon() {}

    @Pointcut("execution(* *..*Service.*(..))")
    private void allService(){}

    @Around("allCoupon() && allService()")
    public Object allServiceAboutCoupon(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }
}
