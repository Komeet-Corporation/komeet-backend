package fr.btssio.komeet.komeetapi.etl.step;

import fr.btssio.komeet.komeetapi.domain.data.*;
import fr.btssio.komeet.komeetapi.etl.processor.*;
import fr.btssio.komeet.komeetapi.repository.*;
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
        RepositoryItemReader<Role> itemReader = itemReader(repository, "roleItemReader", "id");
        FlatFileItemWriter<String> itemWriter = itemWriter("1roles.sql", "roleItemWriter");
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
        RepositoryItemReader<Equipment> itemReader = itemReader(repository, "equipmentItemReader", "id");
        FlatFileItemWriter<String> itemWriter = itemWriter("2equipments.sql", "equipmentItemWriter");
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
        RepositoryItemReader<Image> itemReader = itemReader(repository, "imageItemReader", "id");
        FlatFileItemWriter<String> itemWriter = itemWriter("3images.sql", "imageItemWriter");
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
        RepositoryItemReader<Company> itemReader = itemReader(repository, "companyItemReader", "email");
        FlatFileItemWriter<String> itemWriter = itemWriter("4companies.sql", "companyItemWriter");
        StepBuilder stepBuilder = new StepBuilder("saveCompanyTableStep", jobRepository);
        return stepBuilder
                .<Company, String>chunk(1, new ResourcelessTransactionManager())
                .reader(itemReader)
                .processor(new CompanyItemProcessor())
                .writer(itemWriter)
                .build();
    }

    @Bean
    public Step saveRoomTableStep(RoomRepository repository) {
        RepositoryItemReader<Room> itemReader = itemReader(repository, "roomItemReader", "id");
        FlatFileItemWriter<String> itemWriter = itemWriter("5rooms.sql", "roomItemWriter");
        StepBuilder stepBuilder = new StepBuilder("saveRoomTableStep", jobRepository);
        return stepBuilder
                .<Room, String>chunk(1, new ResourcelessTransactionManager())
                .reader(itemReader)
                .processor(new RoomItemProcessor())
                .writer(itemWriter)
                .build();
    }

    @Bean
    public Step saveUserTableStep(UserRepository repository) {
        RepositoryItemReader<User> itemReader = itemReader(repository, "userItemReader", "email");
        FlatFileItemWriter<String> itemWriter = itemWriter("6users.sql", "userItemWriter");
        StepBuilder stepBuilder = new StepBuilder("saveUserTableStep", jobRepository);
        return stepBuilder
                .<User, String>chunk(1, new ResourcelessTransactionManager())
                .reader(itemReader)
                .processor(new UserItemProcessor())
                .writer(itemWriter)
                .build();
    }

    @Bean
    public Step saveEquipTableStep(RoomRepository repository) {
        RepositoryItemReader<Room> itemReader = itemReader(repository, "equipItemReader", "id");
        FlatFileItemWriter<String> itemWriter = itemWriter("7equips.sql", "equipItemWriter");
        StepBuilder stepBuilder = new StepBuilder("saveEquipTableStep", jobRepository);
        return stepBuilder
                .<Room, String>chunk(1, new ResourcelessTransactionManager())
                .reader(itemReader)
                .processor(new EquipItemProcessor())
                .writer(itemWriter)
                .build();
    }

    @Bean
    Step saveFavoriteTableStep(UserRepository repository) {
        RepositoryItemReader<User> itemReader = itemReader(repository, "favoriteItemReader", "email");
        FlatFileItemWriter<String> itemWriter = itemWriter("8favorites.sql", "favoriteItemWriter");
        StepBuilder stepBuilder = new StepBuilder("saveFavoriteTableStep", jobRepository);
        return stepBuilder
                .<User, String>chunk(1, new ResourcelessTransactionManager())
                .reader(itemReader)
                .processor(new FavoriteItemProcessor())
                .writer(itemWriter)
                .build();
    }

    private <T, ID> @NotNull RepositoryItemReader<T> itemReader(JpaRepository<T, ID> repository, String readerName, String sort) {
        return new RepositoryItemReaderBuilder<T>()
                .name(readerName)
                .repository(repository)
                .methodName("findAll")
                .sorts(Collections.singletonMap(sort, Sort.Direction.ASC))
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
