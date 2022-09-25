package com.bindord.eureka.resourceserver.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

    @Value("${service.ingress.context-path}")
    private String svcContextPath;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri:#{null}}")
    private String jwtIssuerURI;

    @Value("${spring.profiles.active}")
    private String profile;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.csrf().disable();
        if (profile.equals("nosec")) {
            http
                    .authorizeExchange()
                    .pathMatchers("/**").permitAll()
                    .anyExchange().authenticated()
                    .and()
                    .oauth2ResourceServer()
                    .jwt()
                    .jwtAuthenticationConverter(keycloakGrantedAuthoritiesConverter());
            return http.build();
        }
        http
                .authorizeExchange()
//                .pathMatchers("/eureka/resource-server/**").permitAll()
                .pathMatchers("/webjars/**").permitAll()
                .pathMatchers("/swagger**").permitAll()
                .pathMatchers("/v3/**").permitAll()
                .pathMatchers("/actuator/**").permitAll()
                .anyExchange().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(keycloakGrantedAuthoritiesConverter());
        return http.build();
    }

    @Bean
    public KeycloakGrantedAuthoritiesConverter keycloakGrantedAuthoritiesConverter() {
        return new KeycloakGrantedAuthoritiesConverter();
    }

}
