package fr.btssio.komeet.komeetapi.etl.processor;

import fr.btssio.komeet.komeetapi.domain.data.Company;
import fr.btssio.komeet.komeetapi.domain.data.Role;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompanyItemProcessorTest {

    private final CompanyItemProcessor processor = new CompanyItemProcessor();

    @Test
    void process() {
        Company company = createCompany();

        String result = processor.process(company);

        assertEquals("INSERT INTO company (email, uuid, role, name, phone) VALUES ('test@test.test', 'd12ef486-f80b-4558-b294-5e351b0e86f2', 1, 'test', '0000000000');", result);
    }

    private @NotNull Company createCompany() {
        Company company = new Company();
        company.setEmail("test@test.test");
        company.setUuid("d12ef486-f80b-4558-b294-5e351b0e86f2");
        company.setRole(createRole());
        company.setName("test");
        company.setPhone("0000000000");
        return company;
    }

    private @NotNull Role createRole() {
        Role role = new Role();
        role.setId(1L);
        return role;
    }
}