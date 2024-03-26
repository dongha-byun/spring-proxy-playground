package hello.proxy.blog;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

public class CouponServiceAOPTest {

    // Spring AOP 에서 제공하는 포인트컷 객체
    // 포인트컷이 의도한대로 매칭되는지를 확인하기 위해 실제 Aspect 객체를 만들어도 되지만,
    // 테스트 코드로 간편하게 검증하기 위해 해당 객체를 사용
    // pointcut.setExpression(param) : param 표현식을 포인트컷에 적용
    // pointcut.matches(method, Object) : pointcut 이 Object.method()에 적용되는 대상인지 검증
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();

    // pointcut 테스트를 위한 메서드 정보 -> 자바 리플렉션 참고
    Method find; // CouponService.find() 에 대한 정보
    Method delete; // CouponService.delete() 에 대한 정보

    @BeforeEach
    void setup() throws NoSuchMethodException {
        find = CouponService.class.getMethod("find", String.class); // Parameter Type 이 String 인 find 메서드
        delete = CouponService.class.getMethod("delete", Long.class); // Parameter Type 이 String 인 delete 메서드
    }

    @Test
    @DisplayName("모든 내용을 포함시킨 포인트컷 - 접근제어자 / 선언타입(패키지경로) / 예외 패턴 모두 포함") // 보류!! 왜안됨 ㅠ
    void all_matches_pointcut_include_all_pattern() {
        pointcut.setExpression("execution(public void hello.proxy.blog.CouponService.delete(String) IOException)");
        assertThat(pointcut.matches(delete, CouponService.class)).isTrue();
    }

    @Test
    @DisplayName("생략 가능한 내용을 제외한 포인트컷 - 리턴타입 / 메서드명 / 파라미터만 포함")
    void minimum_matches_pointcut() {
        pointcut.setExpression("execution(void delete(Long))");
        assertThat(pointcut.matches(delete, CouponService.class)).isTrue(); // 매칭 O
        assertThat(pointcut.matches(find, CouponService.class)).isFalse(); // 메서드 명과 파라미터 타입이 달라서 매칭 안 됨
    }

    @Test
    @DisplayName("가장 많이 생략한 포인트컷 - 리턴타입 / 메서드명 / 파라미터 가 아무거나 와도 매칭되는 포인트 컷")
    void any_matches_pointcut() {
        // 접근제어자 : 없음(생략됨)
        // 리턴타임 : * (아무거나 다 가능)
        // 선언타입 : 없음(생략됨)
        // 메서드명 : * (아무거나 다 가능)
        // 파라미터 : .. (아무거나 다 가능)
        // 예외 : 없음(생략됨)
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(delete, CouponService.class)).isTrue();
        assertThat(pointcut.matches(find, CouponService.class)).isTrue();
    }

    @Test
    @DisplayName("메서드 이름에 패턴을 적용한 포인트 컷")
    void method_name_pattern_matches_pointcut() {
        pointcut.setExpression("execution(* dele*(..))"); // 메서드 명이 "dele"로 시작하는 메서드
        assertThat(pointcut.matches(delete, CouponService.class)).isTrue();
        assertThat(pointcut.matches(find, CouponService.class)).isFalse();
    }

    @Test
    @DisplayName("선언타입(패키지 + 클래스)를 정확하게 매칭한 포인트 컷")
    void package_class_matches_pointcut() {
        // hello.proxy.blog 패키지 하위의 CouponService 의 모든 메서드
        pointcut.setExpression("execution(* hello.proxy.blog.CouponService.*(..))");
        assertThat(pointcut.matches(delete, CouponService.class)).isTrue();
        assertThat(pointcut.matches(find, CouponService.class)).isTrue();
    }

    @Test
    @DisplayName("특정 패키지 하위를 매칭한 포인트 컷")
    void package_path_matches_pointcut() {
        // hello.proxy.blog 패키지 하위의 모든 클래스 의 모든 메서드
        pointcut.setExpression("execution(* hello.proxy.blog.*.*(..))");
        assertThat(pointcut.matches(delete, CouponService.class)).isTrue();
        assertThat(pointcut.matches(find, CouponService.class)).isTrue();
    }

    @Test
    @DisplayName("특정 파라미터를 정확하게 매칭한 포인트컷")
    void exact_parameter_matches_pointcut() {
        // hello.proxy.blog 패키지 하위의 모든 클래스 의 모든 메서드
        pointcut.setExpression("execution(* *(String))");
        assertThat(pointcut.matches(delete, CouponService.class)).isFalse();
        assertThat(pointcut.matches(find, CouponService.class)).isTrue();
    }

    @Test
    @DisplayName("파라미터가 없는 메서드를 매칭하는 포인트컷")
    void no_parameter_matches_pointcut() {
        pointcut.setExpression("execution(* *())");
        assertThat(pointcut.matches(delete, CouponService.class)).isFalse();
        assertThat(pointcut.matches(find, CouponService.class)).isFalse();
    }

    @Test
    @DisplayName("앞에 파라미터는 일치하고, 뒤에 파라미터는 유동적인 메서드를 매칭하는 포인트컷")
    void first_parameter_exact_after_all_matches_pointcut() {
        pointcut.setExpression("execution(* *(String, ..))"); // 첫 번째 파라미터는 String 이어야 하고, 그 이후론 있든 없든 상관없음
        assertThat(pointcut.matches(delete, CouponService.class)).isFalse();
        assertThat(pointcut.matches(find, CouponService.class)).isTrue();
    }

    @Test
    @DisplayName("within - CouponService 타입에 대해서 부가기능을 적용한다.")
    void within_match_pointcut() {
        pointcut.setExpression("within(hello.proxy.blog.CouponService)");
        assertThat(pointcut.matches(delete, CouponService.class)).isTrue();
        assertThat(pointcut.matches(find, CouponService.class)).isTrue();
    }

    @Test
    @DisplayName("within - CouponService 타입에 대해서 부가기능을 적용한다.")
    void within_parent_type_match_fail() {
        pointcut.setExpression("within(hello.proxy.blog.CouponServiceInterface)");
        assertThat(pointcut.matches(delete, CouponService.class)).isFalse();
        assertThat(pointcut.matches(find, CouponService.class)).isFalse();
    }


    @Test
    @DisplayName("args - 메서드의 파라미터가 일치하면 적용하는 포인트컷")
    void args_matches_pointcut() {
        pointcut.setExpression("args(String)");
        assertThat(pointcut.matches(delete, CouponService.class)).isFalse();
        assertThat(pointcut.matches(find, CouponService.class)).isTrue();
    }

    @Test
    @DisplayName("args - args 는 파라미터가 품을 수 있는 모든 타입에 대해 적용된다. > 파라미터의 부모타입을 지정해도 포인트컷 적용")
    void args_parent_type_matches_pointcut() {
        pointcut.setExpression("args(Object)");
        assertThat(pointcut.matches(delete, CouponService.class)).isTrue();
        assertThat(pointcut.matches(find, CouponService.class)).isTrue();
    }

    @Test
    @DisplayName("args vs execution - args 는 파라미터 타입의 부모도 가능 / execution 은 코드에 명시된 타입만 가능")
    void args_versus_execution_pointcut() {
        // == args 표현식
        AspectJExpressionPointcut argsPointcut = new AspectJExpressionPointcut();
        argsPointcut.setExpression("args(Object)");

        // delete(Long), find(String) 에서 Long 과 String 모두 Object 타입의 상속을 받은 타입이므로
        // args(Object) 로 해도 매치됨
        assertThat(argsPointcut.matches(delete, CouponService.class)).isTrue();
        assertThat(argsPointcut.matches(find, CouponService.class)).isTrue();

        // == execution 표현식
        AspectJExpressionPointcut executionPointcut = new AspectJExpressionPointcut();
        executionPointcut.setExpression("execution(* *(Object))");

        // delete(Long), find(String) 에서 메서드 파라미터 타입이 Long 과 String 으로 명시됐기 때문에
        // execution(* *(Object)) 로 하는 경우 매치 실패
        // 위 매치가 성립하려면, 메서드가 delete(Object) 혹은 find(Object) 형태로 정의되어야 함
        assertThat(executionPointcut.matches(delete, CouponService.class)).isFalse();
        assertThat(executionPointcut.matches(find, CouponService.class)).isFalse();
    }


    @Test
    @DisplayName("@target : 특정 annotation 을 가진 타입에 적용되는 포인트컷 - 부모 타입까지 전파됨")
    void annotation_target_matches_pointcut() throws NoSuchMethodException {
        pointcut.setExpression("@target(hello.proxy.blog.annotation.AopAnnotation)");

        // CouponService 구현체에 대해 매치됨
        assertThat(pointcut.matches(delete, CouponService.class)).isTrue();
        assertThat(pointcut.matches(find, CouponService.class)).isTrue();

        // 부모 타입인 CouponServiceInterface 에 대해서도 매치됨
        Method superProcess = CouponServiceInterface.class.getMethod("superProcess");
        assertThat(pointcut.matches(superProcess, CouponServiceInterface.class)).isTrue();
    }

    @Test
    @DisplayName("@within : 특정 annotation 을 가진 타입에 적용되는 포인트컷 - 부모 타입으로 전파 안됨")
    void annotation_within_matches_pointcut() throws NoSuchMethodException {
        pointcut.setExpression("@within(hello.proxy.blog.annotation.AopAnnotation)");

        // CouponService 구현체에 대해 매치됨
        assertThat(pointcut.matches(delete, CouponService.class)).isTrue();
        assertThat(pointcut.matches(find, CouponService.class)).isTrue();

        // 부모 타입인 CouponServiceInterface 에 대해서는 매치 안 됨
        Method superProcess = CouponServiceInterface.class.getMethod("superProcess");
        assertThat(pointcut.matches(superProcess, CouponServiceInterface.class)).isFalse();
    }

    @Test
    @DisplayName("@annotation : 특정 annotation 을 가진 메서드에 대해 매칭")
    void annotation_matches_pointcut() {
        pointcut.setExpression("@annotation(hello.proxy.blog.annotation.AopAnnotation)");

        assertThat(pointcut.matches(delete, CouponService.class)).isTrue();
        assertThat(pointcut.matches(find, CouponService.class)).isFalse();
    }

}