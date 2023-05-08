package com.bindord.jaipro.resourceserver.configuration.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@ConfigurationProperties(prefix = "eureka.cors")
@Getter
@Setter
public class CorsAddress {

    private Set<String> address;

}
