package com.nineleaps.productservice.config;

import com.nineleaps.productservice.utilities.AppConstant;
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
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.base.package.scan}")
    private String PKG;

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage(PKG))
                .paths(regex(AppConstant.BASE_URI + "/products.*")).build().apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {
        ApiInfo apiInfo = new ApiInfo("Swagger documentation for Product API",
                "Product's API for UI developers", "1.0", "Terms of Service",
                new Contact("Product Service", "https://www.nineleaps.com", "dilip.chauhan@nineleaps.com"),
                "Apache License Version 2.0", "https://www.apache.org/licesen.html", Collections.emptyList());
        return apiInfo;
    }
}

