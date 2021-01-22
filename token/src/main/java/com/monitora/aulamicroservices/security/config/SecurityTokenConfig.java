package com.monitora.aulamicroservices.security.config;


import com.monitora.aulamicroservices.core.property.JwtConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {
    protected final JwtConfiguration jwtConfiguration;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("Has hole {}", http.antMatcher("/aulamicroservices/v1/admin/**").headers());
        http.csrf().disable().cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling().authenticationEntryPoint((req, resp, e) -> resp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .authorizeRequests().antMatchers(jwtConfiguration.getLoginUrl()).permitAll()
                .antMatchers("/aulamicroservices/v1/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();

    }

}
