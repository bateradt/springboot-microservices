package com.monitora.aulamicroservices.auth.security.config;

import com.monitora.aulamicroservices.auth.security.filter.JwtUsernameAndPasswordAuthenticationFilter;
import com.monitora.aulamicroservices.core.property.JwtConfiguration;
import com.monitora.aulamicroservices.security.config.SecurityTokenConfig;
import com.monitora.aulamicroservices.security.filter.JwtTokenAuthorizationFilter;
import com.monitora.aulamicroservices.security.token.converter.TokenConverter;
import com.monitora.aulamicroservices.security.token.creator.TokenCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityCredentialsConfig extends SecurityTokenConfig {
    private final UserDetailsService userDetailsService;
    private final TokenCreator tokenCreator;
    private final TokenConverter tokenConverter;

    public SecurityCredentialsConfig(JwtConfiguration jwtConfiguration, UserDetailsService userDetailsService, TokenCreator tokenCreator, TokenConverter tokenConverter) {
        super(jwtConfiguration);
        this.userDetailsService = userDetailsService;
        this.tokenCreator = tokenCreator;
        this.tokenConverter = tokenConverter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //ele que irá fazer a autenticação chamando o findbyUserName
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfiguration, tokenCreator))
                .addFilterAfter(new JwtTokenAuthorizationFilter(jwtConfiguration, tokenConverter), UsernamePasswordAuthenticationFilter.class);
        super.configure(http);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
