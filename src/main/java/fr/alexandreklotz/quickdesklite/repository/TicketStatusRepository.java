package fr.alexandreklotz.quickdesklite.repository;

import fr.alexandreklotz.quickdesklite.model.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketStatusRepository extends JpaRepository<TicketStatus, Long> {
}
