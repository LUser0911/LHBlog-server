package com.lh.blog.config;

import com.lh.blog.common.config.CustomFailureAuthenticationEntryPoint;
import com.lh.blog.common.config.CustomSuccessAuthenticationHandler;
import com.lh.blog.common.config.LogOutSuccessHandlerImpl;
import com.lh.blog.common.filter.JWTVerifyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/3/31 20:01
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomSuccessAuthenticationHandler customSuccessAuthenticationHandler;


    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JWTVerifyFilter jwtVerifyFilter;

    @Autowired
    LogOutSuccessHandlerImpl logOutSuccessHandler;

    @Autowired
    private CustomFailureAuthenticationEntryPoint customFailureAuthenticationEntryPoint;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling().authenticationEntryPoint(customFailureAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/admin/login").permitAll()
                .antMatchers("/common/**").permitAll()
                .antMatchers("/admin/logout").permitAll()
//                .antMatchers("/**/*.img","/**/*.png","/**/*.jpg","/**/*.gif").permitAll()
                .antMatchers("/images/**").permitAll()
                .anyRequest()
                .authenticated()
                .and().csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.logout().logoutUrl("/admin/logout").logoutSuccessHandler(logOutSuccessHandler);
        http.addFilterBefore(jwtVerifyFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
