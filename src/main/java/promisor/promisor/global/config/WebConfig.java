package promisor.promisor.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import promisor.promisor.global.auth.JwtAuthArgumentResolver;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtAuthArgumentResolver jwtAuthArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(jwtAuthArgumentResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PATCH")
                .allowedOrigins("http://localhost:8080", "http://promisor.site", "https://promisor.site",
                        "http://localhost:3000", "http://api-storage.cloud.toast.com",
                        "http://server.promisor.site", "https://server.promisor.site",
                        "http://promisor-client.s3-website.ap-northeast-2.amazonaws.com",
                        "https://dapi.kakao.com/v2/maps/sdk.js?appkey=175fa8a0273175de3f4f82ba92ebffb8&libraries=services",
                        "https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=cs1gq3mwri&submodules=geocoder");
    }
}
