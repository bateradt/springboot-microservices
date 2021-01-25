package com.monitora.aulamicroservices.token.config;


import com.monitora.aulamicroservices.core.property.JwtConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {
    protected final JwtConfiguration jwtConfiguration;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling().authenticationEntryPoint((req, resp, e) -> resp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .authorizeRequests()
                .antMatchers(jwtConfiguration.getLoginUrl(), "/**/swagger-ui.html", "/**/actuator/info").permitAll()
                .antMatchers(HttpMethod.GET, "/**/swagger-resources/**", "/**/webjars/springfox-swagger-ui/**", "/**/v2/api-docs/**", "/**/swagger-ui").permitAll()
                .antMatchers("/aulamicroservices/v1/admin/**").hasRole("ADMIN")
                .antMatchers("/auth/user/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated();

    }

}
