package com.mihajlo.betbull.playermarket.playerteam.config;

import com.mihajlo.betbull.playermarket.playerteam.controller.ControllerPackage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ComponentScan(basePackageClasses = ControllerPackage.class)
public class RestConfig implements WebMvcConfigurer {

    private static final String REST_BASE_PACKAGE = ControllerPackage.class.getPackage().getName();
    private static final String APP_TITLE = "BetBull Player Market API documentation";
    private static final String APP_DESCRIPTION = "Player market BetBull assignment";
    private static final String APP_VERSION = "1.0";

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage(REST_BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(APP_TITLE)
                .description(APP_DESCRIPTION)
                .version(APP_VERSION)
                .build();
    }
}