package com.monitora.aulamicroservices.auth.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monitora.aulamicroservices.core.model.ApplicationUser;
import com.monitora.aulamicroservices.core.property.JwtConfiguration;
import com.monitora.aulamicroservices.security.token.creator.TokenCreator;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtConfiguration jwtConfiguration;
    private final TokenCreator tokenCreator;

    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        log.info("Attempting authentication...");
        ApplicationUser applicationUser = new ObjectMapper().readValue(request.getInputStream(), ApplicationUser.class);
        if (applicationUser == null) {
            throw new UsernameNotFoundException("Unable to retrieve the username or password");
        }

        log.info("Creating the authentication object for the user '{}' and calling UserDetailServiceImpl loadUserByUsername", applicationUser.getUsername());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(applicationUser.getUsername(), applicationUser.getPassword(), Collections.emptyList());
        usernamePasswordAuthenticationToken.setDetails(applicationUser);

        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    @Override
    @SneakyThrows
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) {
        log.info("Authentication was successful for the user '{}', generating JWT token", auth.getName());
        //criar o JWT assinado
        SignedJWT signedJWT = tokenCreator.createSignedJWT(auth);

        //depois criptograr o JWT
        String encryptedToken = tokenCreator.encryptToken(signedJWT);

        log.info("Token generated successfuly, adding it to the response header");

        response.addHeader("Access-Control-Expose-Headers", "XSRF-TOKEN, " + jwtConfiguration.getHeader().getName());

        response.addHeader(jwtConfiguration.getHeader().getName(), jwtConfiguration.getHeader().getPrefix() + encryptedToken);

    }

}

//quando estiver retornando um token, primeiro devemos assinar o token e depois criptografalo
