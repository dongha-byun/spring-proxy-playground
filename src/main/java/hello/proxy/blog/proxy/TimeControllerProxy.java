package hello.proxy.blog.proxy;

import hello.proxy.blog.CouponControllerInterface;
import hello.proxy.blog.dto.CouponCreateRequest;
import hello.proxy.blog.dto.CouponResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

@Slf4j
@RequiredArgsConstructor
public class TimeControllerProxy implements CouponControllerInterface {

    private final CouponControllerInterface target;

    @Override
    public ResponseEntity<CouponResponse> create(CouponCreateRequest couponCreateRequest) {
        long start = System.currentTimeMillis();
        log.info("CouponController start - by proxy");

        ResponseEntity<CouponResponse> result = target.create(couponCreateRequest);

        long end = System.currentTimeMillis();
        log.info("CouponController end - by proxy => {}ms", end - start);

        return result;
    }
}
