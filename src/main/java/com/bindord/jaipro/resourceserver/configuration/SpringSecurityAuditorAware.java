package com.bindord.jaipro.resourceserver.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import reactor.core.publisher.Mono;

@Configuration
@EnableR2dbcAuditing
public class SpringSecurityAuditorAware implements ReactiveAuditorAware<String> {

    public static final String NON_AUTHENTICATED_USER = "public-user";

    @Override
    public Mono<String> getCurrentAuditor() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(auth -> ((CustomUserDetails) auth).getUsername())
                .switchIfEmpty(Mono.just(NON_AUTHENTICATED_USER));
    }
}
