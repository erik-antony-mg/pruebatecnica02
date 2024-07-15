package com.amg.pruebatecnica02.infrastructure.configuration;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;


@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API USUARIOS",
                description = "Una api que registra, busca, elimina  usuarios , incluye seguridad y el login ",
                termsOfService = "www.terminos-condiciones.com",
                version = "1.0.0",
                contact = @Contact(
                        name = "erik antony",
                        url = "erik.antony.com",
                        email = "erik@mail.com"
                ),
                license = @License(
                        name = "Standard Software Use License for UnProgramadorNace",
                        url = "url de la licencia"
                )
        ),
        servers = {
                @Server(
                        description = "DEV SERVER",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "PROD SERVER",
                        url = "http://unprogramadornace:8080"
                )
        }
//        ,
//        security = @SecurityRequirement(
//                name = "Security Token"
//        )
)
@SecurityScheme(
        name = "Security Token",
        description = "Access Token",
        type = SecuritySchemeType.HTTP,
        paramName = HttpHeaders.AUTHORIZATION,
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
}
