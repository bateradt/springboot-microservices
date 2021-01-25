package com.monitora.aulamicroservices.auth.docs;

import com.monitora.aulamicroservices.core.docs.BaseSwaggerConfig;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    public SwaggerConfig() {
        super("com.monitora.aulamicroservices.auth.endpoint.controller");
    }
}