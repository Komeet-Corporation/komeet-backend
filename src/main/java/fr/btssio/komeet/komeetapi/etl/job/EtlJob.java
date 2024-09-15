package fr.btssio.komeet.komeetapi.etl.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.DuplicateJobException;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EtlJob {

    public static final String JOB_NAME = "komeetEtlJob";

    private final JobBuilder jobBuilder;

    public EtlJob(JobBuilder jobBuilder) {
        this.jobBuilder = jobBuilder;
    }

    @Bean(name = JOB_NAME)
    public Job job() throws DuplicateJobException, NoSuchJobException {
        Job job = jobBuilder
                .incrementer(new RunIdIncrementer())
                .preventRestart()
                .start(new Step() { //TODO
                    @Override
                    public String getName() {
                        return "Test";
                    }

                    @Override
                    public void execute(StepExecution stepExecution) throws JobInterruptedException {
                        System.out.println(stepExecution.getJobExecution().getJobInstance().getJobName());
                    }
                }).build();
        System.out.println(job.getName());
        return job;
    }
}
