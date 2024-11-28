package fr.btssio.komeet.etl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"fr.btssio.komeet.common", "fr.btssio.komeet.etl"})
@ComponentScan(basePackages = {"fr.btssio.komeet.common", "fr.btssio.komeet.etl"})
@EnableJpaRepositories(basePackages = {"fr.btssio.komeet.common", "fr.btssio.komeet.etl"})
public class KomeetEtlApplication {

    public static void main(String[] args) {
        SpringApplication.run(KomeetEtlApplication.class, args);
    }

}
