package com.nineleaps.orderservice.config;

import com.nineleaps.orderservice.utilities.AppConstants;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * The type Swagger config.
 *
 * @author Dilip Chauhan
 * Created on 22/03/2020
 */
@Configuration
@EnableSwagger2
@NoArgsConstructor
public class SwaggerConfig {

    /**
     * App package to scan for swagger UI
     */
    @Value("${swagger.base.package.scan}")
    private String packageName;

    /**
     * Swagger api docket.
     *
     * @return the docket
     */
    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage(packageName))
                .paths(regex(AppConstants.BASE_URI + "/orders.*")).build().apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {
        return new ApiInfo("Swagger documentation for Order API",
                "Order's API for UI developers", "1.0", "Terms of Service",
                new Contact("Order Service", "https://www.nineleaps.com", "dilip.chauhan@nineleaps.com"),
                "Apache License Version 2.0", "https://www.apache.org/licesen.html", Collections.emptyList());
    }
}

