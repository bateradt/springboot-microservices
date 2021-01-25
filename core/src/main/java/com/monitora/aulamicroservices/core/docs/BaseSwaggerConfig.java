package com.monitora.aulamicroservices.core.docs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Slf4j
public class BaseSwaggerConfig {
    private final String basePackage;

    public BaseSwaggerConfig(String basePackage) {
        this.basePackage = basePackage;
    }

    @Bean
    public Docket api() {
        log.info("basePackage: {}", basePackage);
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any()) //coloquei isso e funcionou.
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        log.info("ApiInfo - metaData()");
        return new ApiInfoBuilder()
                .title("Microservices Estudo Monitora")
                .description("Curso de microservices")
                .version("1.0")
                .contact(new Contact("Marcelo Fernando Scarpim", "http://bateradt.com", "marcelo.scarpim@monitoratec.com.br"))
                .license("MIT")
                .licenseUrl("http://bateradt.com/license")
                .build();
    }

}
