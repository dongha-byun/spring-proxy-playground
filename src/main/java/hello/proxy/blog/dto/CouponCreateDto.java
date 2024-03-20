package hello.proxy.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CouponCreateDto {
    private String name;
    private String grade;
    private int discountRate;
}
