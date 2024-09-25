package fr.btssio.komeet.komeetapi;

import fr.btssio.komeet.komeetapi.util.LogUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class KomeetApiApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(@NotNull SpringApplicationBuilder application) {
        return application.sources(KomeetApiApplication.class);
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(KomeetApiApplication.class, args);
        Environment env = context.getEnvironment();
        Class<KomeetApiApplication> clazz = KomeetApiApplication.class;

        LogUtils.logInfo(clazz, "==================================================");
        LogUtils.logInfo(clazz, env.getProperty("application.name") + " started on version " + env.getProperty("application.version"));
        LogUtils.logInfo(clazz, "==================================================");
    }
}
