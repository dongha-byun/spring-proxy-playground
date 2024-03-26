package hello.proxy.config.blog.coupon;

import hello.proxy.blog.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CouponProxyConfig {
    @Autowired
    private CouponService couponService;
//
//    @Bean
//    public CouponControllerInterface couponControllerInterface() {
//        CouponControllerInterface target = new CouponController(couponService);
//        return new TimeControllerProxy(target);
//    }
}
