package fr.alexandreklotz.quickdesk.dao;

import fr.alexandreklotz.quickdesk.model.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractorDao extends JpaRepository<Contractor, Integer> {
}
