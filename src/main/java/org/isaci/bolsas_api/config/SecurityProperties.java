package org.isaci.bolsas_api.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.api")
@Getter
@Setter
public class SecurityProperties {
    private String key;

    @PostConstruct
    public void printApiKeyInfo() {
        System.out.println("🔐 API Key carregada com sucesso: " + (key != null ? "OK" : "NULA"));
    }

}
