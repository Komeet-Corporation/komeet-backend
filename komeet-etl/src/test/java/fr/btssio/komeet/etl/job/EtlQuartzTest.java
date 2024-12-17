package fr.btssio.komeet.etl.job;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EtlQuartzTest {

    private final JobLocator jobLocator = mock(JobLocator.class);
    private final JobLauncher jobLauncher = mock(JobLauncher.class);
    private final JobExecution jobExecution = mock(JobExecution.class);
    private final ExecutorService executorService = mock(ExecutorService.class);
    private final Job job = mock(Job.class);
    private final EtlQuartz quartz = new EtlQuartz();

    private MockedStatic<Executors> executors;

    @BeforeEach
    void setup() {
        executors = mockStatic(Executors.class);
    }

    @AfterEach
    void teardown() {
        executors.close();
    }

    @Test
    void executeInternal() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException, NoSuchJobException {
        Future<?> future = mock(Future.class);
        quartz.setJobLauncher(jobLauncher);
        quartz.setJobLocator(jobLocator);
        executors.when(Executors::newSingleThreadExecutor).thenReturn(executorService);
        doReturn(future).when(executorService).submit(any(Runnable.class));
        when(jobLauncher.run(any(Job.class), any(JobParameters.class))).thenReturn(jobExecution);
        when(jobLocator.getJob(anyString())).thenReturn(job);

        quartz.executeInternal(createJobExecutionContext());

        verify(executorService, times(1)).submit(any(Runnable.class));
    }

    @Test
    void etlRunnableError() throws NoSuchJobException, JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        Future<?> future = mock(Future.class);
        quartz.setJobLauncher(jobLauncher);
        quartz.setJobLocator(jobLocator);
        executors.when(Executors::newSingleThreadExecutor).thenReturn(executorService);
        doReturn(future).when(executorService).submit(any(Runnable.class));
        when(jobLocator.getJob(anyString())).thenReturn(job);
        when(jobLauncher.run(any(Job.class), any(JobParameters.class))).thenThrow(JobInstanceAlreadyCompleteException.class);

        quartz.executeInternal(createJobExecutionContext());

        verify(executorService, times(1)).submit(any(Runnable.class));
    }

    @Test
    void getJobLocator() {
        quartz.setJobLocator(jobLocator);

        assertEquals(jobLocator, quartz.getJobLocator());
    }

    @Test
    void getJobLauncher() {
        quartz.setJobLauncher(jobLauncher);

        assertEquals(jobLauncher, quartz.getJobLauncher());
    }

    private @NotNull JobExecutionContext createJobExecutionContext() {
        JobExecutionContext jobExecutionContext = mock(JobExecutionContext.class);
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobName", "komeetEtlJob");
        when(jobExecutionContext.getMergedJobDataMap()).thenReturn(jobDataMap);
        return jobExecutionContext;
    }
}