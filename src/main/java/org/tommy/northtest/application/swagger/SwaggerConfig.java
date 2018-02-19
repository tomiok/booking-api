package org.tommy.northtest.application.swagger;

import java.time.Instant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket apis() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("apis")
        .useDefaultResponseMessages(false)
        .directModelSubstitute(Instant.class, Long.class)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.ant("/booking/**"))
        .build();
  }
}
