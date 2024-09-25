package fr.btssio.komeet.komeetapi.etl.job;

import fr.btssio.komeet.komeetapi.util.DateUtils;
import fr.btssio.komeet.komeetapi.util.LogUtils;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.quartz.JobExecutionContext;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Map;
import java.util.concurrent.Executors;

@Getter
@Setter
@Configuration
public class EtlQuartz extends QuartzJobBean {

    private static final String START_TIME = "startTime";
    private static final String FORMAT_START_TIME = "formatStartTime";
    private static final String JOB_NAME = "jobName";
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    private JobLocator jobLocator;
    private JobLauncher jobLauncher;

    @Override
    protected void executeInternal(@NotNull JobExecutionContext context) {
        Runnable etl = createEtlRunnable(context);
        Executors.newSingleThreadExecutor().submit(etl);
    }

    @Contract(pure = true)
    private @NotNull Runnable createEtlRunnable(@NotNull JobExecutionContext context) {
        return () -> {
            JobParametersBuilder parameters = new JobParametersBuilder();
            long startTime = System.currentTimeMillis();
            parameters.addLong(START_TIME, startTime);
            parameters.addString(FORMAT_START_TIME, DateUtils.formatMillisTime(startTime, FORMAT));

            try {
                Map<String, Object> map = context.getMergedJobDataMap();
                String jobName = (String) map.get(JOB_NAME);
                jobLauncher.run(jobLocator.getJob(jobName), parameters.toJobParameters());
            } catch (Exception e) {
                LogUtils.logError(this.getClass(), "Error BATCH", e);
            }
        };
    }
}
