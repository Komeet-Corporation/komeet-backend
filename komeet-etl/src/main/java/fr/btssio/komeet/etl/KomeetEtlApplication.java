package fr.btssio.komeet.etl;

import fr.btssio.komeet.common.config.KomeetCommonModuleConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(KomeetCommonModuleConfiguration.class)
public class KomeetEtlApplication {

    public static void main(String[] args) {
        SpringApplication.run(KomeetEtlApplication.class, args);
    }

}
