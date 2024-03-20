package hello.proxy.blog;

import hello.proxy.blog.dto.CouponCreateDto;
import org.springframework.stereotype.Service;

@Service
public class CouponService {

    public Long create(CouponCreateDto couponCreateDto) {
        return 10L;
    }
}
