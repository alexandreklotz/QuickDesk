package fr.alexandreklotz.quickdesk.repository;

import fr.alexandreklotz.quickdesk.model.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContractorRepository extends JpaRepository<Contractor, UUID> {

    @Query("FROM Contractor c WHERE c.contractorName = :name")
    Optional<Contractor> getContractorByName(@Param("name")String name);
}
