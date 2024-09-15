package fr.btssio.komeet.komeetapi.etl.step;

import fr.btssio.komeet.komeetapi.domain.data.Role;
import fr.btssio.komeet.komeetapi.etl.processor.RoleItemProcessor;
import fr.btssio.komeet.komeetapi.repository.RoleRepository;
import fr.btssio.komeet.komeetapi.service.PathService;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.WritableResource;
import org.springframework.data.domain.Sort;

import java.util.Collections;

@Configuration
public class SaveTableStep {

    private final PathService pathService;
    private final JobRepository jobRepository;

    public SaveTableStep(PathService pathService, JobRepository jobRepository) {
        this.pathService = pathService;
        this.jobRepository = jobRepository;
    }

    @Bean
    public RepositoryItemReader<Role> roleItemReader(RoleRepository roleRepository) {
        return new RepositoryItemReaderBuilder<Role>()
                .name("roleItemReader")
                .repository(roleRepository)
                .methodName("findAll")
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public FlatFileItemWriter<String> roleItemWriter() {
        DelimitedLineAggregator<String> delimiter = new DelimitedLineAggregator<>();
        delimiter.setDelimiter("\n");
        WritableResource resource = (WritableResource) pathService.getTempFileAsResource("roles.sql");
        return new FlatFileItemWriterBuilder<String>()
                .name("roleItemWriter")
                .resource(resource)
                .lineAggregator(delimiter)
                .build();
    }

    @Bean
    public Step saveRoleTableStep(RepositoryItemReader<Role> roleItemReader, FlatFileItemWriter<String> roleItemWriter) {
        StepBuilder stepBuilder = new StepBuilder("saveRoleTableStep", jobRepository);
        return stepBuilder
                .<Role, String>chunk(1, new ResourcelessTransactionManager())
                .reader(roleItemReader)
                .processor(new RoleItemProcessor())
                .writer(roleItemWriter)
                .build();
    }
}
