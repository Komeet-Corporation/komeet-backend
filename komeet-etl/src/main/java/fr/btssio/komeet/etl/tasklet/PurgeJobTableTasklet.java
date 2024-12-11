package fr.btssio.komeet.etl.tasklet;

import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class PurgeJobTableTasklet implements Tasklet {

    private static final String SCRIPT_STEP_EXECUTION = "DELETE FROM BATCH_STEP_EXECUTION WHERE JOB_EXECUTION_ID != %d";
    private static final String SCRIPT_JOB_EXECUTION_PARAMS = "DELETE FROM BATCH_JOB_EXECUTION_PARAMS WHERE JOB_EXECUTION_ID != %d";
    private static final String SCRIPT_JOB_EXECUTION_CONTEXT = "DELETE FROM BATCH_JOB_EXECUTION_CONTEXT WHERE JOB_EXECUTION_ID != %d";
    private static final String SCRIPT_JOB_EXECUTION = "DELETE FROM BATCH_JOB_EXECUTION WHERE JOB_INSTANCE_ID != %d";
    private static final String SCRIPT_JOB_INSTANCE = "DELETE FROM BATCH_JOB_INSTANCE WHERE JOB_INSTANCE_ID != %d";
    private static final String SCRIPT_STEP_EXECUTION_CONTEXT = "DELETE FROM BATCH_STEP_EXECUTION_CONTEXT WHERE STEP_EXECUTION_ID IN " +
            "(SELECT STEP_EXECUTION_ID FROM BATCH_STEP_EXECUTION WHERE JOB_EXECUTION_ID != %d);";

    private final DataSource dataSource;

    public PurgeJobTableTasklet(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public RepeatStatus execute(@NotNull StepContribution contribution, @NotNull ChunkContext chunkContext) throws SQLException {

        final Connection connection = dataSource.getConnection();
        final long jobInstanceId = contribution.getStepExecution().getJobExecution().getJobInstance().getInstanceId();
        final long jobExecutionId = contribution.getStepExecution().getJobExecution().getId();

        final Resource stepExecutionContextRequest = new ByteArrayResource(String.format(SCRIPT_STEP_EXECUTION_CONTEXT, jobExecutionId).getBytes());
        final Resource stepExecutionRequest = new ByteArrayResource(String.format(SCRIPT_STEP_EXECUTION, jobExecutionId).getBytes());
        final Resource jobExecutionParams = new ByteArrayResource(String.format(SCRIPT_JOB_EXECUTION_PARAMS, jobExecutionId).getBytes());
        final Resource jobExecutionContext = new ByteArrayResource(String.format(SCRIPT_JOB_EXECUTION_CONTEXT, jobExecutionId).getBytes());
        final Resource jobExecution = new ByteArrayResource(String.format(SCRIPT_JOB_EXECUTION, jobInstanceId).getBytes());
        final Resource jobInstance = new ByteArrayResource(String.format(SCRIPT_JOB_INSTANCE, jobInstanceId).getBytes());

        ScriptUtils.executeSqlScript(connection, stepExecutionContextRequest);
        ScriptUtils.executeSqlScript(connection, stepExecutionRequest);
        ScriptUtils.executeSqlScript(connection, jobExecutionParams);
        ScriptUtils.executeSqlScript(connection, jobExecutionContext);
        ScriptUtils.executeSqlScript(connection, jobExecution);
        ScriptUtils.executeSqlScript(connection, jobInstance);

        connection.close();

        return RepeatStatus.FINISHED;
    }
}
