package promisor.promisor.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import promisor.promisor.domain.member.service.MemberLoginFailHandler;
import promisor.promisor.domain.member.service.MemberService;
import promisor.promisor.global.PasswordEncoder;
import promisor.promisor.global.config.security.CustomAuthenticationFilter;
import promisor.promisor.global.config.security.JwtProvider;
import promisor.promisor.global.config.security.jwtAuthenticationFilter;
import promisor.promisor.global.error.CustomAuthenticationEntryPoint;
import promisor.promisor.global.error.WebAccessDeniedHandler;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtProvider jwtProvider;
    private final WebAccessDeniedHandler webAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPointHandler;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/login");
        http
                .httpBasic().disable()
                        .csrf().disable()
                        .sessionManagement().sessionCreationPolicy(STATELESS)
                        .and()
                                .authorizeRequests()
                                        .antMatchers("/member/**", "/login/**", "/friends/**").permitAll()
                        .anyRequest().authenticated()
                        .and()
                                .exceptionHandling()
                                        .authenticationEntryPoint(authenticationEntryPointHandler)
                                                .accessDeniedHandler(webAccessDeniedHandler)
                                                        .and()
                                                                .addFilterBefore(new jwtAuthenticationFilter(jwtProvider),
                                                                        UsernamePasswordAuthenticationFilter.class)


        http.csrf().disable();
        http.formLogin().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/login/**").permitAll();
        http.authorizeRequests().antMatchers("/members/**").permitAll();
        http.authorizeRequests().antMatchers("/friends/**").permitAll();
        http.authorizeRequests().antMatchers(GET, "/members/**").permitAll();
        http.authorizeRequests().antMatchers(POST, "/store/save/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint()).and().addFilter(customAuthenticationFilter);
        http.addFilterAt(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
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
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder.bCryptPasswordEncoder());
        provider.setUserDetailsService(memberService);

        return provider;
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new MemberLoginFailHandler();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
