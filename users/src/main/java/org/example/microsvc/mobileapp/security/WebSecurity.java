package org.example.microsvc.mobileapp.security;

import org.example.microsvc.mobileapp.ui.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private Environment environment;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserServiceInterface userService;

    @Autowired
    public WebSecurity(Environment environment,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       UserServiceInterface userService){
       this.environment = environment;
       this.userService = userService;
       this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        // allow all requests for users
        http.authorizeRequests()
                .antMatchers("/users/**").permitAll()
                .and()
                .addFilter(getAuthenticationFilter());
        // allow requests only from gateway server
        /* http.authorizeRequests().antMatchers("/**").hasIpAddress("192.168.1.217")
         .and()
         .addFilter(getAuthenticationFilter());*/
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(environment, userService);
        authenticationFilter.setAuthenticationManager(authenticationManager());
        authenticationFilter.setFilterProcessesUrl("/users/login");

        return authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder);
    }
}
