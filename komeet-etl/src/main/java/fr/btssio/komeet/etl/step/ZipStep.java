package fr.btssio.komeet.etl.step;

import fr.btssio.komeet.etl.config.EtlConfig;
import fr.btssio.komeet.etl.job.EtlJob;
import fr.btssio.komeet.etl.tasklet.DeleteOldZipTasklet;
import fr.btssio.komeet.etl.tasklet.ZipAndSaveTasklet;
import fr.btssio.komeet.common.service.PathService;
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
        StepBuilder stepBuilder = new StepBuilder(EtlJob.ZIP_AND_SAVE_STEP, etlConfig.getJobRepository());
        return stepBuilder
                .tasklet(new ZipAndSaveTasklet(pathService), new ResourcelessTransactionManager())
                .build();
    }

    @Bean
    public Step deleteOldZipStep() {
        StepBuilder stepBuilder = new StepBuilder(EtlJob.DELETE_OLD_ZIP_STEP, etlConfig.getJobRepository());
        return stepBuilder
                .tasklet(new DeleteOldZipTasklet(pathService), new ResourcelessTransactionManager())
                .build();
    }
}
