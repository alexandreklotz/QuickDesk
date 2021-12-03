package fr.alexandreklotz.quickdesk.dao;

import fr.alexandreklotz.quickdesk.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractDao extends JpaRepository<Contract, Integer> {
}
