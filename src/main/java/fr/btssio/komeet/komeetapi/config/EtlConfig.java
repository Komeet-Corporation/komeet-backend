package fr.btssio.komeet.komeetapi.config;

import fr.btssio.komeet.komeetapi.etl.job.EtlJob;
import fr.btssio.komeet.komeetapi.etl.job.EtlQuartz;
import lombok.Getter;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Configuration
public class EtlConfig {

    private static final String JOB_LAUNCHER = "jobLauncher";
    private static final String JOB_NAME = "jobName";
    private static final String JOB_LOCATOR = "jobLocator";

    private final JobRepository jobRepository;
    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    @Value("${etl.job.cron}")
    private String jobCron;

    public EtlConfig(JobRepository jobRepository, JobLauncher jobLauncher, JobRegistry jobRegistry) {
        this.jobRepository = jobRepository;
        this.jobLauncher = jobLauncher;
        this.jobRegistry = jobRegistry;
    }

    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(EtlQuartz.class);
        jobDetailFactoryBean.setDurability(true);
        Map<String, Object> map = new HashMap<>();
        map.put(JOB_LAUNCHER, jobLauncher);
        map.put(JOB_NAME, EtlJob.JOB_NAME);
        jobDetailFactoryBean.setJobDataAsMap(map);
        return jobDetailFactoryBean;
    }

    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(Objects.requireNonNull(jobDetailFactoryBean().getObject()));
        cronTriggerFactoryBean.setCronExpression(jobCron);
        return cronTriggerFactoryBean;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(cronTriggerFactoryBean().getObject());
        Map<String, Object> map = new HashMap<>();
        map.put(JOB_LAUNCHER, jobLauncher);
        map.put(JOB_LOCATOR, jobRegistry);
        schedulerFactoryBean.setSchedulerContextAsMap(map);
        return schedulerFactoryBean;
    }

    @Bean
    public JobBuilder jobBuilder() {
        return new JobBuilder(EtlJob.JOB_NAME, jobRepository);
    }
}
