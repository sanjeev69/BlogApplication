package com.sanjeev.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.AuthorizationScope;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	
	public static final String AUTHORIZATION_HEADER="Authorization";
	
	private ApiKey apiKeys() {
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}
	
	private List<SecurityContext> securityContexts(){
		return Arrays.asList(SecurityContext.builder().securityReferences(securityReferences()).build()) ;
	}
	
	private List<SecurityReference> securityReferences(){
		AuthorizationScope scope = new AuthorizationScope("global", "accecc everythinfg");
		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] {scope}));
	}
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2) 
				.apiInfo(appinfo())
				.securityContexts(securityContexts())
				.securitySchemes(Arrays.asList(apiKeys()))
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo appinfo() {
		return new ApiInfo("Blogging Application Backend",
				"This project is developed by Sanjeev Kumar","1.0" , "Terms of service", new Contact("Sanjeev", null, "sanjeevkumaryadav188@gmail.com"), "Licence", "Api Licence URL",Collections.EMPTY_LIST);
	}
}