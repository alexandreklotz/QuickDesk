package fr.alexandreklotz.quickdesk.dao;

import fr.alexandreklotz.quickdesk.model.Software;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoftwareDao extends JpaRepository<Software, Integer> {
}
