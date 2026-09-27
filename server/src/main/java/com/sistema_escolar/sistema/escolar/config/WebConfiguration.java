package com.sistema_escolar.sistema.escolar.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080",
                        "http://localhost:5173",
                        "http://localhost",
                        "https://sistema-academico-v2.vercel.app",
                        "https://si-bce7166fa7a74f649ca10761b0fba808.ecs.us-east-1.on.aws")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
