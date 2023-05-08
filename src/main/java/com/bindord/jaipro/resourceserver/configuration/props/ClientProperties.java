package com.bindord.jaipro.resourceserver.configuration.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "internal.services.eureka-clients")
@Getter
@Setter
public class ClientProperties {

    private ClientConfig keycloakAuth;

    @Getter
    @Setter
    public static class ClientConfig {
        private String url;
        private Integer readTimeout;
        private Integer writeTimeout;
        private Integer connectionTimeout;
    }
}
