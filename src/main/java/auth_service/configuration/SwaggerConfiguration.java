package auth_service.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "JWT API",
                description = "Implementación de JWT con Spring Boot",
                termsOfService = "https://edisonrivera.github.io/",
                version = "1.0.0",
                contact = @Contact(name = "EsTX", url = "https://edisonrivera.github.io/", email = "test@gmail.com")
        )
)
@Configuration
public class SwaggerConfiguration {
        @Bean
        public OpenAPI openAPI() {
                return new OpenAPI().addSecurityItem(new SecurityRequirement().
                                addList("Bearer Authentication"))
                        .components(new Components().addSecuritySchemes
                                ("Bearer Authentication", createAPIKeyScheme()));
        }

        private SecurityScheme createAPIKeyScheme() {
                return new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT");
        }
}

