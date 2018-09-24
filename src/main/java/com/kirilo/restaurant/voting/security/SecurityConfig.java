package com.kirilo.restaurant.voting.security;

import com.kirilo.restaurant.voting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//http://www.springboottutorial.com/securing-rest-services-with-spring-boot-starter-security
@EnableWebSecurity
@Configuration
//@Order(SecurityProperties.BASIC_AUTH_ORDER - 10)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService service;

    // Authorization : Role -> Access
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().httpBasic().and()
                .authorizeRequests()
//                .antMatchers("/").permitAll()
                .antMatchers("/rest/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/rest/restaurants/**").hasAnyRole("ADMIN")
                .antMatchers("/rest/user/**").hasAnyRole("USER")
                .anyRequest().denyAll();
//                .anyRequest().fullyAuthenticated();
    }

//For "In memory Authentication"
/*    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser("user@yandex.ru").password("password").roles("USER")
                .and()
                .withUser("admin@gmail.com").password("password").roles("ADMIN");
    }*/

}
