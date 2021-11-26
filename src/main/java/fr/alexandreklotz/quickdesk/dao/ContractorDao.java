package fr.alexandreklotz.quickdesk.dao;

import fr.alexandreklotz.quickdesk.model.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractorDao extends JpaRepository<Contractor, Integer> {
}
