package fr.btssio.komeet.api.service;

import fr.btssio.komeet.common.data.Company;
import fr.btssio.komeet.api.dto.CompanyDto;
import fr.btssio.komeet.api.exception.ConflictException;
import fr.btssio.komeet.api.mapper.CompanyMapper;
import fr.btssio.komeet.common.repository.CompanyRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    public CompanyDto findByEmail(String email) throws ConflictException {
        Company company = companyRepository.findById(email).orElseThrow(() -> new ConflictException("Company doesn't exist : " + email));
        return companyMapper.toDto(company);
    }
}
