package com.tidb.hackathon.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * 在生产环境中必须关闭swagger
 */
@Profile("dev")
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.info.name}")
    private String title;

    @Value("${swagger.info.group}")
    private String group;

    @Value("${swagger.info.version}")
    private String version;

    @Value("${swagger.info.contact.name}")
    private String author;

    @Value("${swagger.info.contact.mail}")
    private String mail;

    /**
     * 文档摘要信息
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .forCodeGeneration(true)
                .apiInfo(new ApiInfoBuilder()
                        .title(this.title)
                        .description(this.title + " Swagger2 API")
                        .termsOfServiceUrl("http://tidb_hackathon_2022.tidb.com")
                        .version(this.version)
                        .contact(new Contact(this.author, "", this.mail))
                        .build())
                .groupName(this.group)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tidb.hackathon"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .protocols(new LinkedHashSet<>(Arrays.asList("http", "https")));
    }
}
