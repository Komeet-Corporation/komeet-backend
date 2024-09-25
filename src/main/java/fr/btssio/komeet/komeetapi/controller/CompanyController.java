package fr.btssio.komeet.komeetapi.controller;

import fr.btssio.komeet.komeetapi.domain.dto.CompanyDto;
import fr.btssio.komeet.komeetapi.domain.exception.ConflictException;
import fr.btssio.komeet.komeetapi.service.CompanyService;
import fr.btssio.komeet.komeetapi.util.LogUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<CompanyDto> getByEmail(@PathVariable String email) {
        try {
            CompanyDto companyDto = companyService.findByEmail(email);
            LogUtils.logInfo(this.getClass(), companyDto.toString());
            return ResponseEntity.status(HttpStatus.OK).body(companyDto);
        } catch (ConflictException e) {
            LogUtils.logWarn(this.getClass(), e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            LogUtils.logError(this.getClass(), "Error during getting company by email", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
