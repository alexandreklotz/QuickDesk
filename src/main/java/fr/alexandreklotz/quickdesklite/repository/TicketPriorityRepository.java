package fr.alexandreklotz.quickdesklite.repository;

import fr.alexandreklotz.quickdesklite.model.TicketPriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketPriorityRepository extends JpaRepository<TicketPriority, Long> {
}
