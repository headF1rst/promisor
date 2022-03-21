package promisor.promisor.domain.auth;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AuthenticationInterceptor.class)
public @interface EnableAuth {
}
