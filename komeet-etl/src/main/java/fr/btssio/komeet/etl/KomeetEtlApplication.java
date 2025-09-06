package fr.btssio.komeet.etl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"fr.btssio.komeet.common", "fr.btssio.komeet.etl"})
@EntityScan(basePackages = {"fr.btssio.komeet.common", "fr.btssio.komeet.etl"})
@EnableJpaRepositories(basePackages = {"fr.btssio.komeet.common", "fr.btssio.komeet.etl"})
public class KomeetEtlApplication {

    public static void main(String[] args) {
        SpringApplication.run(KomeetEtlApplication.class, args);
    }

}
