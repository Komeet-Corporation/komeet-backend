package fr.btssio.komeet.komeetapi.controller;

import fr.btssio.komeet.komeetapi.dto.CompanyDto;
import fr.btssio.komeet.komeetapi.service.CompanyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyController {

    private static final String PATH = "/company/";

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping(PATH + "{email}")
    public CompanyDto getByEmail(@PathVariable String email) {
        return this.companyService.findByEmail(email);
    }
}
