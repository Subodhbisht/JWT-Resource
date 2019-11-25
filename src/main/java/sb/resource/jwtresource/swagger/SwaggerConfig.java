
package sb.resource.jwtresource.swagger;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import com.google.common.collect.Lists;

import sb.resource.jwtresource.JwtResourceApplication;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration

@EnableSwagger2
public class SwaggerConfig {

	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

	@Bean
	public Docket api() {
		System.out.println("Base package is " + JwtResourceApplication.class.getPackage().getName());
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage(JwtResourceApplication.class.getPackage().getName())).build()
				.apiInfo(new ApiInfo("User Detail API", "Sample user detail API", "1.0", "Free",
						new Contact("Subodh Bisht", "7814880178", "A@b.com"), "Api Licence", "subodh.io",
						Collections.emptyList()))
				.forCodeGeneration(true).genericModelSubstitutes(ResponseEntity.class) //
//  .ignoredParameterTypes(Pageable.class)
				.ignoredParameterTypes(java.sql.Date.class)
				.directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
				.directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
				.directModelSubstitute(java.time.LocalDateTime.class, Date.class)
				.securityContexts(Lists.newArrayList(securityContext())).securitySchemes(Lists.newArrayList(apiKey()));

	}

	private ApiKey apiKey() {
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
				 .securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN)).build();
	}
	
	List<SecurityReference> defaultAuth() {
	    AuthorizationScope authorizationScope
	            = new AuthorizationScope("global", "accessEverything");
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	    authorizationScopes[0] = authorizationScope;
	    return Lists.newArrayList(
	            new SecurityReference("JWT", authorizationScopes));
	}
}
