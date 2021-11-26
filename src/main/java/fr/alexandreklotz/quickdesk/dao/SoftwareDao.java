package fr.alexandreklotz.quickdesk.dao;

import fr.alexandreklotz.quickdesk.model.Software;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoftwareDao extends JpaRepository<Software, Integer> {
}
