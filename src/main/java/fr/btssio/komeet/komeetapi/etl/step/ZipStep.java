package fr.btssio.komeet.komeetapi.etl.step;

import fr.btssio.komeet.komeetapi.config.EtlConfig;
import fr.btssio.komeet.komeetapi.etl.tasklet.DeleteOldZipTasklet;
import fr.btssio.komeet.komeetapi.etl.tasklet.ZipAndSaveTasklet;
import fr.btssio.komeet.komeetapi.service.PathService;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZipStep {

    private final EtlConfig etlConfig;
    private final PathService pathService;

    public ZipStep(EtlConfig etlConfig, PathService pathService) {
        this.etlConfig = etlConfig;
        this.pathService = pathService;
    }

    @Bean
    public Step zipAndSaveStep() {
        StepBuilder stepBuilder = new StepBuilder("zipAndSaveStep", etlConfig.getJobRepository());
        return stepBuilder
                .tasklet(new ZipAndSaveTasklet(pathService), new ResourcelessTransactionManager())
                .build();
    }

    @Bean
    public Step deleteOldZipStep() {
        StepBuilder stepBuilder = new StepBuilder("deleteOldZipStep", etlConfig.getJobRepository());
        return stepBuilder
                .tasklet(new DeleteOldZipTasklet(pathService), new ResourcelessTransactionManager())
                .build();
    }
}
