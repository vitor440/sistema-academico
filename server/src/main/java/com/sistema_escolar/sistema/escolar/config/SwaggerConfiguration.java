package com.sistema_escolar.sistema.escolar.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Sistema escolar",
        description = "Sistema para ajudar alunos e professores a gerenciarem tarefas como: gerenciar notas, " +
                "consultar datas de exames, faltas e notas, realizar matrícula em matérias, ver status da disciplina e outros.",
        contact = @Contact(
                name = "Vitor de Souza Oliveira",
                email = "vitor16.souzaoliver@gmail.com"
        ),
        version = "v1"
))
@SecurityRequirement(name = "bearerAuth")
@SecurityScheme(name = "bearerAuth", bearerFormat = "JWT", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER, scheme = "bearer")
public class SwaggerConfiguration {

}
