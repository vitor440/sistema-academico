package com.sistema_escolar.sistema.escolar.config;

import com.sistema_escolar.sistema.escolar.security.JwtAuthenticationFilter;
import com.sistema_escolar.sistema.escolar.security.TestFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecutityConfiguration {

    @Value("${frontend.uri}")
    private String clientUri;

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter filter) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl(clientUri + "/login")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID"))
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/swagger-ui/**", "/v3/api-docs",
                            "/v3/api-docs/**", "/swagger-ui.html", "/actuator/health", "/actuator/health/**").permitAll()
                            .anyRequest().authenticated();
                })

                .addFilterAfter(filter, BearerTokenAuthenticationFilter.class)
                .oauth2ResourceServer(oauth2Rs -> oauth2Rs.jwt(Customizer.withDefaults()))
                .cors(Customizer.withDefaults())
                .build();
    }


}
