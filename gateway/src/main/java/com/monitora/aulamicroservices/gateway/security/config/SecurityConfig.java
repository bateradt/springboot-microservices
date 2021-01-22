package com.monitora.aulamicroservices.gateway.security.config;

import com.monitora.aulamicroservices.core.property.JwtConfiguration;
import com.monitora.aulamicroservices.gateway.security.filter.GatewayJwtTokenAuthorizationFilter;
import com.monitora.aulamicroservices.security.config.SecurityTokenConfig;
import com.monitora.aulamicroservices.security.token.converter.TokenConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends SecurityTokenConfig {
    private final TokenConverter tokenConverter;

    public SecurityConfig(JwtConfiguration jwtConfiguration, TokenConverter tokenConverter) {
        super(jwtConfiguration);
        this.tokenConverter = tokenConverter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(new GatewayJwtTokenAuthorizationFilter(jwtConfiguration, tokenConverter), UsernamePasswordAuthenticationFilter.class);
        super.configure(http);
    }
}
