package promisor.promisor.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(title = "Promsior API 명세서",
        description = "API 명세서",
        version = "v1",
        contact = @Contact(name = "juy4556", email = "kws05050@naver.com"),
        license = @License(name = "Apache 2.0",
            url = "http://www.apache.org/licenenses/LICENSE-2.0.html")
    )
)
@Configuration
public class OpenApiConfig {
    @Bean
    public GroupedOpenApi memberApi(){
        String[] paths = {"/members/**"};
        return GroupedOpenApi.builder().group("멤버 관련 API").pathsToMatch(paths).build();
    }

    @Bean
    public GroupedOpenApi teamApi(){
        String[] paths = {"/groups/**"};
        return GroupedOpenApi.builder().group("팀 관련 API").pathsToMatch(paths).build();
    }
    @Bean
    public GroupedOpenApi banDateApi(){
        String[] paths = {"/bandate/**"};
        return GroupedOpenApi.builder().group("일정 관련 API").pathsToMatch(paths).build();
    }
    @Bean
    public GroupedOpenApi promiseApi(){
        String[] paths = {"/promises/**"};
        return GroupedOpenApi.builder().group("약속 관련 API").pathsToMatch(paths).build();
    }
    @Bean
    public GroupedOpenApi friendApi(){
        String[] paths = {"/friends/**"};
        return GroupedOpenApi.builder().group("친구 관련 API").pathsToMatch(paths).build();
    }
}