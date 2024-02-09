package fr.btssio.komeet.komeetapi.repository;

import fr.btssio.komeet.komeetapi.data.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
}
