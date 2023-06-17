package com.bindord.jaipro.resourceserver.configuration.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cloud.gcp")
@Getter
@Setter
public class GcpProperties {

    private String projectId;

    @Value("${cloud.gcp.storage.bucket}")
    private String storageBucket;
}
