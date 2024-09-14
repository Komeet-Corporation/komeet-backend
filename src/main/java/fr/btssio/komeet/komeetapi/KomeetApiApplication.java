package fr.btssio.komeet.komeetapi;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
public class KomeetApiApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(@NotNull SpringApplicationBuilder application) {
        return application.sources(KomeetApiApplication.class);
    }

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
