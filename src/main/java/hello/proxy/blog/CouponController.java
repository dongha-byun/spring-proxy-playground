package hello.proxy.blog;

import hello.proxy.blog.dto.CouponCreateDto;
import hello.proxy.blog.dto.CouponCreateRequest;
import hello.proxy.blog.dto.CouponResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CouponController {
    private final CouponService couponService;

    @PostMapping("/coupons")
    public ResponseEntity<CouponResponse> create(CouponCreateRequest couponCreateRequest) {
        // 1. 로직 시작 시간 측정
        //long start = System.currentTimeMillis();

        CouponCreateDto couponCreateDto = couponCreateRequest.toDto();
        Long couponId = couponService.create(couponCreateDto);

        // 2. return 직전에 종료 시간 측정
//        long end = System.currentTimeMillis();
//        log.info("CouponController 소요 시간 => {}ms", end-start);

        return ResponseEntity.created(URI.create("/coupons/"+couponId)).body(
                new CouponResponse(couponId, "쿠폰이 정상적으로 등록되었습니다.")
        );
    }
}
