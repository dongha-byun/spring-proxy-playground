package hello.proxy.blog;

import hello.proxy.blog.annotation.AopAnnotation;
import hello.proxy.blog.dto.CouponCreateDto;
import java.io.IOException;
import org.springframework.stereotype.Service;

@AopAnnotation
@Service
public class CouponService implements CouponServiceInterface{

    public Long create(CouponCreateDto couponCreateDto) {
        return 10L;
    }

    public String find(String name) {
        return name;
    }

    @AopAnnotation
    public void delete(Long id) throws IOException {

    }

    @Override
    public void superProcess() {

    }
}
