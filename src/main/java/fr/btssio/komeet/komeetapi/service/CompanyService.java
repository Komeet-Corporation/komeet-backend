package fr.btssio.komeet.komeetapi.service;

import fr.btssio.komeet.komeetapi.data.Company;
import fr.btssio.komeet.komeetapi.dto.CompanyDto;
import fr.btssio.komeet.komeetapi.repository.CompanyRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public CompanyDto findByEmail(String email) {
        Company company = this.companyRepository.findById(email).orElse(null);
        return company != null ? CompanyDto.bindFromCompany(company) : null;
    }
}
