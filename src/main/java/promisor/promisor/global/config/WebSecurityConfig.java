package promisor.promisor.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import promisor.promisor.domain.member.service.CustomUserDetailService;
import promisor.promisor.domain.member.service.MemberLoginFailHandler;
import promisor.promisor.global.PasswordEncoder;
import promisor.promisor.global.config.security.CustomAuthenticationFilter;
import promisor.promisor.global.config.security.JwtAuthenticationFilter;
import promisor.promisor.global.config.security.JwtProvider;
import promisor.promisor.global.error.CustomAuthenticationEntryPoint;
import promisor.promisor.global.error.WebAccessDeniedHandler;
import promisor.promisor.global.secret.SecretKey;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtProvider jwtProvider;
    private final CustomUserDetailService customUserDetailService;
    private final PasswordEncoder passwordEncoder;
    private final SecretKey secretKey;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        http
                .httpBasic().disable()
                        .csrf().disable()
                        .sessionManagement().sessionCreationPolicy(STATELESS)
                        .and()
                                .authorizeRequests()
                                        .antMatchers("/members/**", "/login/**", "/friends/**").permitAll()
                        .anyRequest().authenticated()
                        .and()
                                .exceptionHandling()
                                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                                                .accessDeniedHandler(new WebAccessDeniedHandler())
                                                        .and()
                                                                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider),
                                                                        UsernamePasswordAuthenticationFilter.class);
    }

    protected CustomAuthenticationFilter getAuthenticationFilter() throws Exception {

        CustomAuthenticationFilter authFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        try {
            authFilter.setFilterProcessesUrl("/login");
            authFilter.setAuthenticationManager(this.authenticationManagerBean());
            authFilter.setUsernameParameter("email");
            authFilter.setPasswordParameter("password");

        } catch (Exception err) {
            err.printStackTrace();
        }
        return authFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder.bCryptPasswordEncoder());
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new MemberLoginFailHandler();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
