package hello.proxy.blog;

import hello.proxy.blog.dto.CouponCreateRequest;
import hello.proxy.blog.dto.CouponResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@RequestMapping
public interface CouponControllerInterface {

    @PostMapping("/coupons")
    ResponseEntity<CouponResponse> create(@RequestBody CouponCreateRequest couponCreateRequest);
}
