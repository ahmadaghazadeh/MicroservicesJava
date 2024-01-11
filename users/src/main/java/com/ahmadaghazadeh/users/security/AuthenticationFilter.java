package com.ahmadaghazadeh.users.security;

import com.ahmadaghazadeh.users.services.UserService;
import com.ahmadaghazadeh.users.shared.UserDto;
import com.ahmadaghazadeh.users.ui.models.LoginRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;
    private final Environment environment;

    public AuthenticationFilter(UserService userService,
                                Environment environment,
                                AuthenticationManager authenticationManager){

        this.userService = userService;
        this.environment = environment;
        super.setAuthenticationManager(authenticationManager);
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        try {
            LoginRequestModel loginRequestModel = new ObjectMapper().readValue(req.getInputStream(), LoginRequestModel.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestModel.getEmail(),
                            loginRequestModel.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        String userName = ((User) auth.getPrincipal()).getUsername();
        UserDto userDetails = userService.getUserByEmail(userName);
        String tokenSecret = environment.getProperty("token.secret");
        byte[] secretKeyBytes = Base64.getEncoder().encode(tokenSecret.getBytes());
        SecretKey signingKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());
        String token = Jwts.builder()
                .setSubject(userDetails.getUserId())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration_time"))))
                .signWith(signingKey)
                .compact();
        res.addHeader("token", token);
        res.addHeader("userId", userDetails.getUserId());
    }
}
