package by.ryni.share;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
//@EnableOpenApi
@ComponentScan(basePackages = {"org.springdoc"})
//@EnableWebMvc
//@Import({SpringDataRestConfiguration.class, BeanValidatorPluginsConfiguration.class})
public class SwaggerConfiguration extends WebMvcConfigurationSupport {
    private final static String PATH_PATTERNS = "swagger-ui.html";
    private final static String RESOURCE_LOCATION = "classpath:/META-INF/resources/";
    private final static String WEB_JAR_PATH_PATTERNS = "/webjars/**";
    private final static String WEB_JAR_RESOURCE_LOCATION = "classpath:/META-INF/resources/webjars/";

    @Value("${swagger.base.package}")
    private String basePackage;

    private Info apiInfo() {
        return new Info().title("Rest API").version("1.0.0")
                .description("This is a Rest Spring service for share experiences");
    }

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(apiInfo())
                .components(new Components()
                     .addSecuritySchemes("bearer-key", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .in(SecurityScheme.In.HEADER)
                                .scheme("bearer")
                                .bearerFormat("jwt")
                                .name("JWT Authentication")
                        )
                )
                .addSecurityItem(new SecurityRequirement()
                        .addList("bearer-key")
                );
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(PATH_PATTERNS)
                .addResourceLocations(RESOURCE_LOCATION);

        registry.addResourceHandler(WEB_JAR_PATH_PATTERNS)
                .addResourceLocations(WEB_JAR_RESOURCE_LOCATION);
    }
}
