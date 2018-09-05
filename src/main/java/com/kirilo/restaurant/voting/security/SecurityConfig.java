package com.kirilo.restaurant.voting.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

//http://www.springboottutorial.com/securing-rest-services-with-spring-boot-starter-security
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // Authorization : Role -> Access
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().authorizeRequests().antMatchers("/rest/admin/**").hasRole("ADMIN")
                .antMatchers("/rest/**").hasRole("USER").and()
                .csrf().disable().headers().frameOptions().disable();
    }
    // Authentication : User --> Roles
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
        .withUser("user@yandex.ru").password("password").roles("USER").and()
        .withUser("admin@gmail.com").password("password").roles("ADMIN");
    }
}
