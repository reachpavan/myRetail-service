package org.pavan.starter.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by parepu on 2/23/2016.
 */
@Configuration
@EnableSwagger2
public class SwaggerClientConfig {
  private static final Logger logger = LoggerFactory.getLogger(SwaggerClientConfig.class);

  @Bean
  public Docket productApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("product")
        .apiInfo(apiInfo())
        .select()
        .paths(PathSelectors.ant("/v1/product/**/*"))
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Product Lookup API")
        .description("Product Lookup API with Swagger")
        .version("1.0")
        .build();
  }
}
