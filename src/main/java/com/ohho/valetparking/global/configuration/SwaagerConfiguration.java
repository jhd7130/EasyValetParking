package com.ohho.valetparking.global.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/

@Configuration
public class SwaagerConfiguration {

        @Bean
        public Docket api() {
            return new Docket(DocumentationType.OAS_30)
                    .useDefaultResponseMessages(false)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.ohho.valetparking.domain"))
                    .paths(PathSelectors.any())
                    .build()
                    .apiInfo(apiInfo());
        }

        private ApiInfo apiInfo() {
            return new ApiInfoBuilder()
                    .title("Swagger Test")
                    .description("SwaggerConfig")
                    .version("3.0")
                    .build();
        }

}

