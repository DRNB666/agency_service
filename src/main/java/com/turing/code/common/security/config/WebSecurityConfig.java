package com.turing.code.common.security.config;

import com.turing.code.common.security.handle.EntryPointUnauthorizedHandler;
import com.turing.code.common.security.handle.RestAccessDeniedHandler;
import com.turing.code.common.security.jwt.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.annotation.Resource;

/**
 * 安全模块配置
 *
 * @author Turing
 * Created on 2017/12/8
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static PasswordEncoder passwordEncoder;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Resource
    private EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;
    @Resource
    private RestAccessDeniedHandler restAccessDeniedHandler;
    @Value("${springfox.documentation.enabled}")
    private boolean swaggerEnable;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        if (passwordEncoder == null) {
            passwordEncoder = new BCryptPasswordEncoder();
        }
        authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //添加转码
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        httpSecurity.addFilterBefore(encodingFilter, ChannelProcessingFilter.class);
        httpSecurity.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers("/**/public/**", "/**/files/**", "/**/portal/**", "/", "/**/ws/**", "/**/static/**", "/**/error/**").permitAll()
                .antMatchers("/druid/**").permitAll()
                .antMatchers("/user/**").access("hasRole('" + SecurityConfig.ROLE_USER + "')")
                .antMatchers("/admin/**").access("hasRole('" + SecurityConfig.ROLE_ADMIN + "')")
                .antMatchers("/tenant/**").access("hasRole('" + SecurityConfig.ROLE_TENANT + "')")
                .anyRequest().authenticated()
                .and().headers().cacheControl();
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.exceptionHandling().authenticationEntryPoint(entryPointUnauthorizedHandler).accessDeniedHandler(restAccessDeniedHandler);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/",
                "/swagger-resources/**",
                "/v2/api-docs",
                "/configuration/security",
                "/swagger-ui/**",
                "/webjars/**",
                "/druid/**",
                "/static/**",
                "/error/**",
                "/doc.html"
        );
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}