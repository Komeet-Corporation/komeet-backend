package fr.btssio.komeet.api;

import fr.btssio.komeet.common.config.KomeetCommonModuleConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
@Import(KomeetCommonModuleConfiguration.class)
public class KomeetApiApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(KomeetApiApplication.class, args);
        Environment env = context.getEnvironment();

        log.info("==================================================");
        log.info("{} started on version {}",
                env.getProperty("application.name"),
                env.getProperty("application.version"));
        log.info("==================================================");
    }
}
