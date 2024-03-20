package hello.proxy.blog;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.proxy.blog.dto.CouponCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CouponController.class)
class CouponControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CouponService couponService;

    @Test
    @DisplayName("쿠폰을 생성한다.")
    void create() throws Exception {
        when(couponService.create(any())).thenReturn(1L);

        CouponCreateRequest couponCreateRequest = new CouponCreateRequest("쿠폰 #1", "VIP", 10);
        String requestBody = objectMapper.writeValueAsString(couponCreateRequest);

        mockMvc.perform(post("/coupons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.couponId", is(1)))
                .andExpect(jsonPath("$.message", is("쿠폰이 정상적으로 등록되었습니다.")));
    }
}