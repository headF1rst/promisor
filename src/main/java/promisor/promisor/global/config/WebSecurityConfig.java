package promisor.promisor.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import promisor.promisor.domain.member.MemberService;
import promisor.promisor.global.PasswordEncoder;
import promisor.promisor.global.config.security.CustomAuthenticationFilter;
import promisor.promisor.global.config.security.CustomAuthorizationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/login");
        http.csrf().disable();
        http.formLogin().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/login/**").permitAll();
        http.authorizeRequests().antMatchers("/members/**").permitAll();
        http.authorizeRequests().antMatchers(GET, "/members/**").permitAll();
        http.authorizeRequests().antMatchers(POST, "/store/save/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
//        http.addFilter(customAuthenticationFilter);
        http.addFilterAt(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
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

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(passwordEncoder.bCryptPasswordEncoder());
        provider.setUserDetailsService(memberService);

        log.info("Provider : {}", provider);
        return provider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
