package com.monitora.aulamicroservices.auth.security.config;

import com.monitora.aulamicroservices.auth.security.filter.JwtUsernameAndPasswordAuthenticationFilter;
import com.monitora.aulamicroservices.config.SecurityTokenConfig;
import com.monitora.aulamicroservices.core.property.JwtConfiguration;
import com.monitora.aulamicroservices.token.creator.TokenCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityCredentialsConfig extends SecurityTokenConfig {
    private final UserDetailsService userDetailsService;
    private final TokenCreator tokenCreator;

    public SecurityCredentialsConfig(JwtConfiguration jwtConfiguration, UserDetailsService userDetailsService, TokenCreator tokenCreator) {
        super(jwtConfiguration);
        this.userDetailsService = userDetailsService;
        this.tokenCreator = tokenCreator;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //ele que irá fazer a autenticação chamando o findbyUserName
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfiguration, tokenCreator));
        super.configure(http);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
