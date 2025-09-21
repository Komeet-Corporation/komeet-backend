package fr.btssio.komeet.common.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "fr.btssio.komeet.common")
@EntityScan(basePackages = "fr.btssio.komeet.common")
@EnableJpaRepositories(basePackages = "fr.btssio.komeet.common")
public class KomeetCommonModuleConfiguration {
}
