package com.kirilo.restaurant.voting.security;

import com.kirilo.restaurant.voting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

//http://ryanjbaxter.com/2015/01/06/securing-rest-apis-with-spring-boot/
@Configuration
public class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {
    @Autowired
    UserService service;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Bean
    UserDetailsService userDetailsService() {
        return username -> {
            com.kirilo.restaurant.voting.model.User user = service.getByEmail(username);
            if (user != null) {
//                return new User(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true,
//                        user.getRoles());
                return new AuthorizedUser(user);
            } else {
                throw new UsernameNotFoundException("could not find the user '"
                        + username + "'");
            }
        };
    }
}
