package fr.btssio.komeet.etl.step;

import fr.btssio.komeet.etl.config.EtlConfig;
import fr.btssio.komeet.etl.job.EtlJob;
import fr.btssio.komeet.etl.tasklet.DeleteTempFileTasklet;
import fr.btssio.komeet.etl.tasklet.WriteRestoreFileTasklet;
import fr.btssio.komeet.common.service.PathService;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileStep {

    private final EtlConfig etlConfig;
    private final PathService pathService;

    public FileStep(EtlConfig etlConfig, PathService pathService) {
        this.etlConfig = etlConfig;
        this.pathService = pathService;
    }

    @Bean
    public Step deleteTempFileStep() {
        StepBuilder stepBuilder = new StepBuilder(EtlJob.DELETE_TEMP_FILE_STEP, etlConfig.getJobRepository());
        return stepBuilder
                .tasklet(new DeleteTempFileTasklet(pathService), new ResourcelessTransactionManager())
                .build();
    }

    @Bean
    public Step writeRestoreFileStep() {
        StepBuilder stepBuilder = new StepBuilder(EtlJob.WRITE_RESTORE_FILE_STEP, etlConfig.getJobRepository());
        return stepBuilder
                .tasklet(new WriteRestoreFileTasklet(pathService), new ResourcelessTransactionManager())
                .build();
    }
}
