package org.isaci.bolsas_api;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootApplication
public class BolsasApiApplication {

    private static final Logger log = LoggerFactory.getLogger(BolsasApiApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(BolsasApiApplication.class, args);
        Environment env = ctx.getEnvironment();
        log.info("🚀 Perfil ativo: {}", String.join(", ", env.getActiveProfiles()));
    }

}
