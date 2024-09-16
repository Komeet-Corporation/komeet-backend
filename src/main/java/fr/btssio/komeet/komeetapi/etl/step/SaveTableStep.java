package fr.btssio.komeet.komeetapi.etl.step;

import fr.btssio.komeet.komeetapi.domain.data.Company;
import fr.btssio.komeet.komeetapi.domain.data.Equipment;
import fr.btssio.komeet.komeetapi.domain.data.Image;
import fr.btssio.komeet.komeetapi.domain.data.Role;
import fr.btssio.komeet.komeetapi.etl.processor.CompanyItemProcessor;
import fr.btssio.komeet.komeetapi.etl.processor.EquipmentItemProcessor;
import fr.btssio.komeet.komeetapi.etl.processor.ImageItemProcessor;
import fr.btssio.komeet.komeetapi.etl.processor.RoleItemProcessor;
import fr.btssio.komeet.komeetapi.repository.CompanyRepository;
import fr.btssio.komeet.komeetapi.repository.EquipmentRepository;
import fr.btssio.komeet.komeetapi.repository.ImageRepository;
import fr.btssio.komeet.komeetapi.repository.RoleRepository;
import fr.btssio.komeet.komeetapi.service.PathService;
import org.jetbrains.annotations.NotNull;
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
import org.springframework.data.jpa.repository.JpaRepository;

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
    public Step saveRoleTableStep(RoleRepository repository) {
        RepositoryItemReader<Role> itemReader = itemReader(repository, "roleItemReader");
        FlatFileItemWriter<String> itemWriter = itemWriter("roles.sql", "roleItemWriter");
        StepBuilder stepBuilder = new StepBuilder("saveRoleTableStep", jobRepository);
        return stepBuilder
                .<Role, String>chunk(1, new ResourcelessTransactionManager())
                .reader(itemReader)
                .processor(new RoleItemProcessor())
                .writer(itemWriter)
                .build();
    }

    @Bean
    public Step saveEquipmentTableStep(EquipmentRepository repository) {
        RepositoryItemReader<Equipment> itemReader = itemReader(repository, "equipmentItemReader");
        FlatFileItemWriter<String> itemWriter = itemWriter("equipments.sql", "equipmentItemWriter");
        StepBuilder stepBuilder = new StepBuilder("saveEquipmentTableStep", jobRepository);
        return stepBuilder
                .<Equipment, String>chunk(1, new ResourcelessTransactionManager())
                .reader(itemReader)
                .processor(new EquipmentItemProcessor())
                .writer(itemWriter)
                .build();
    }

    @Bean
    public Step saveImageTableStep(ImageRepository repository) {
        RepositoryItemReader<Image> itemReader = itemReader(repository, "imageItemReader");
        FlatFileItemWriter<String> itemWriter = itemWriter("images.sql", "imageItemWriter");
        StepBuilder stepBuilder = new StepBuilder("saveImageTableStep", jobRepository);
        return stepBuilder
                .<Image, String>chunk(1, new ResourcelessTransactionManager())
                .reader(itemReader)
                .processor(new ImageItemProcessor())
                .writer(itemWriter)
                .build();
    }

    @Bean
    public Step saveCompanyTableStep(CompanyRepository repository) {
        RepositoryItemReader<Company> itemReader = itemReader(repository, "companyItemReader");
        FlatFileItemWriter<String> itemWriter = itemWriter("companies.sql", "companiesItemWriter");
        StepBuilder stepBuilder = new StepBuilder("saveCompanyTableStep", jobRepository);
        return stepBuilder
                .<Company, String>chunk(1, new ResourcelessTransactionManager())
                .reader(itemReader)
                .processor(new CompanyItemProcessor())
                .writer(itemWriter)
                .build();
    }

    private <T, ID> @NotNull RepositoryItemReader<T> itemReader(JpaRepository<T, ID> repository, String readerName) {
        return new RepositoryItemReaderBuilder<T>()
                .name(readerName)
                .repository(repository)
                .methodName("findAll")
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    private @NotNull FlatFileItemWriter<String> itemWriter(String filename, String writerName) {
        DelimitedLineAggregator<String> delimiter = new DelimitedLineAggregator<>();
        delimiter.setDelimiter("\n");
        WritableResource resource = (WritableResource) pathService.getTempFileAsResource(filename);
        return new FlatFileItemWriterBuilder<String>()
                .name(writerName)
                .resource(resource)
                .lineAggregator(delimiter)
                .build();
    }
}
