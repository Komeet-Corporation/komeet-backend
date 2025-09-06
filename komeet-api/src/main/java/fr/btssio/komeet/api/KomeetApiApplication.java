package fr.btssio.komeet.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@SpringBootApplication(scanBasePackages = {"fr.btssio.komeet.common", "fr.btssio.komeet.api"})
@EntityScan(basePackages = {"fr.btssio.komeet.common", "fr.btssio.komeet.api"})
@EnableJpaRepositories(basePackages = {"fr.btssio.komeet.common", "fr.btssio.komeet.api"})
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
