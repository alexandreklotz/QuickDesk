package fr.alexandreklotz.quickdesk.repository;

import fr.alexandreklotz.quickdesk.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContractRepository extends JpaRepository<Contract, UUID> {

    @Query("FROM Contract c WHERE c.ctrNumber = :nbr")
    Optional<Contract> getContractByContractNumber(@Param("nbr")String nbr);

    @Query("FROM Contract c WHERE c.ctrName = :name")
    Optional<Contract> getContractByContractName(@Param("name")String name);
}
