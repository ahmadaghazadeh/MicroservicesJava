package com.ahmadaghazadeh.users.security;

import com.ahmadaghazadeh.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;


@Configuration
@EnableWebSecurity
public class WebSecurity {
    private final UserService usersService;
    private final Environment environment;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final BCryptPasswordEncoder encoder;


    @Autowired
    public WebSecurity(Environment environment
            ,AuthenticationConfiguration authenticationConfiguration
             ,BCryptPasswordEncoder encoder
             ,UserService usersService
    ) {
        this.usersService = usersService;
        this.environment = environment;
        this.authenticationConfiguration = authenticationConfiguration;
        this.encoder = encoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(a->{
            a.requestMatchers(antMatcher("/users/**")).permitAll();
            a.requestMatchers(antMatcher("/**")).permitAll();
            a.anyRequest().authenticated();
            //a.requestMatchers("/**").permitAll();
            //hasIpAddress(environment.getProperty("gateway.ip"));
        });
 //       http.authorizeRequests()
 //               .requestMatchers(new AntPathRequestMatcher("/users/**")).permitAll()
//                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
//                .requestMatchers(new AntPathRequestMatcher("/api-docs/**")).permitAll()
//                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll() // hasIpAddress(environment.getProperty("gateway.ip"))
 //              .anyRequest().authenticated();
        http.addFilterBefore(getAuthenticationFilter(http), UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(authenticationProvider());
        http.headers(h->{
            h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
        });
        return http.getOrBuild();
    }

    public AuthenticationFilter getAuthenticationFilter(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(usersService, environment,
                authenticationConfiguration.getAuthenticationManager());
        authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
        return authenticationFilter;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(usersService);
        authenticationProvider.setPasswordEncoder(encoder);
        return authenticationProvider;
    }
}