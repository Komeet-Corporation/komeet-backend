package fr.btssio.komeet.api.etl.processor;

import fr.btssio.komeet.api.domain.data.Company;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;

public class CompanyItemProcessor implements ItemProcessor<Company, String> {

    @Override
    public String process(@NotNull Company company) {
        return String.format("INSERT INTO company (email, uuid, role, name, phone) VALUES ('%s', '%s', %d, '%s', '%s');",
                company.getEmail(),
                company.getUuid(),
                company.getRole().getId(),
                company.getName(),
                company.getPhone());
    }
}
