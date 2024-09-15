package fr.btssio.komeet.komeetapi.etl.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EtlJob {

    public static final String JOB_NAME = "komeetEtlJob";

    private static final String DELETE_TEMP_FILE_STEP = "deleteTempFileStep";
    private static final String SAVE_ROLE_TABLE_STEP = "saveRoleTableStep";

    private final JobBuilder jobBuilder;

    public EtlJob(JobBuilder jobBuilder) {
        this.jobBuilder = jobBuilder;
    }

    @Bean(name = JOB_NAME)
    public Job job(
            @Qualifier(DELETE_TEMP_FILE_STEP) Step deleteTempFileStep,
            @Qualifier(SAVE_ROLE_TABLE_STEP) Step saveRoleTableStep
    ) {
        return jobBuilder
                .incrementer(new RunIdIncrementer())
                .preventRestart()
                .start(deleteTempFileStep)
                .next(saveRoleTableStep)
                .build();
    }
}
