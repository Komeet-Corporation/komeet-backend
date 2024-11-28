package fr.btssio.komeet.common.repository;

import fr.btssio.komeet.common.data.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
}
