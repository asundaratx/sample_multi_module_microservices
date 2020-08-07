package org.example.microsvc.mobileapp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.microsvc.mobileapp.model.data.LoginRequestModel;
import org.example.microsvc.mobileapp.model.data.UserModel;
import org.example.microsvc.mobileapp.model.response.UserRest;
import org.example.microsvc.mobileapp.ui.service.UserServiceInterface;
import org.example.microsvc.mobileapp.ui.utils.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private Environment environment;
    private UserServiceInterface userService;

    public AuthenticationFilter(Environment environment,
                                UserServiceInterface userService){
        this.environment = environment;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestModel creds = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginRequestModel.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken
                            (creds.getUsername(), creds.getPassword(), new ArrayList<>()));
        } catch (IOException e){
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
    String username = ((User)authResult.getPrincipal()).getUsername();
        UserRest usermodel = userService.getUserByEmail(username);
        String token = Jwts.builder().setSubject(usermodel.getUserId())
                .setExpiration(new Date(System.currentTimeMillis()+ Long.parseLong(environment.getProperty("token.expiration_time"))
                )).signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
                .compact();
        System.out.println("token : "+ token);
        response.addHeader("token", token);
        response.addHeader("userId", usermodel.getUserId());
    }
}
