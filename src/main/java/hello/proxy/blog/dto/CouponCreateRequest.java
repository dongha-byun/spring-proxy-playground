package hello.proxy.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CouponCreateRequest {

    private String name;
    private String userGrade;
    private int discountRate;

    public CouponCreateDto toDto() {
        return new CouponCreateDto(
                name, userGrade, discountRate
        );
    }
}
