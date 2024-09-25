package fr.btssio.komeet.komeetapi.etl.step;

import fr.btssio.komeet.komeetapi.config.EtlConfig;
import fr.btssio.komeet.komeetapi.etl.tasklet.PurgeJobTableTasklet;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JobTableStep {

    private final DataSource dataSource;
    private final EtlConfig etlConfig;

    public JobTableStep(EtlConfig etlConfig, DataSource dataSource) {
        this.etlConfig = etlConfig;
        this.dataSource = dataSource;
    }

    @Bean
    public Step purgeJobTableStep() {
        StepBuilder stepBuilder = new StepBuilder("purgeJobTableStep", etlConfig.getJobRepository());
        return stepBuilder
                .tasklet(new PurgeJobTableTasklet(dataSource), new ResourcelessTransactionManager())
                .build();
    }
}
