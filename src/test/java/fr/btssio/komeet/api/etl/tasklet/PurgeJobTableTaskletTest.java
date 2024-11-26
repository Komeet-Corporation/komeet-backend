package fr.btssio.komeet.api.etl.tasklet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PurgeJobTableTaskletTest {

    private final DataSource dataSource = mock(DataSource.class);
    private final StepContribution contribution = mock(StepContribution.class);
    private final ChunkContext chunkContext = mock(ChunkContext.class);
    private final PurgeJobTableTasklet tasklet = new PurgeJobTableTasklet(dataSource);

    private MockedStatic<ScriptUtils> scriptUtils;

    @BeforeEach
    void setup() {
        scriptUtils = mockStatic(ScriptUtils.class);
    }

    @AfterEach
    void teardown() {
        scriptUtils.close();
    }

    @Test
    void execute() throws SQLException {
        Connection connection = mock(Connection.class);
        StepExecution stepExecution = mock(StepExecution.class);
        JobExecution jobExecution = mock(JobExecution.class);
        JobInstance jobInstance = mock(JobInstance.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(contribution.getStepExecution()).thenReturn(stepExecution);
        when(stepExecution.getJobExecution()).thenReturn(jobExecution);
        when(jobExecution.getJobInstance()).thenReturn(jobInstance);
        when(jobInstance.getInstanceId()).thenReturn(1L);
        when(jobExecution.getId()).thenReturn(1L);
        doNothing().when(connection).close();
        scriptUtils.when(() -> ScriptUtils.executeSqlScript(any(), (Resource) any())).thenAnswer(invocation -> null);

        RepeatStatus result = tasklet.execute(contribution, chunkContext);

        assertEquals(RepeatStatus.FINISHED, result);
    }
}