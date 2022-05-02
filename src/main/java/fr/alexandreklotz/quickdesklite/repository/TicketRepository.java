package fr.alexandreklotz.quickdesklite.repository;

import fr.alexandreklotz.quickdesklite.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("FROM Ticket t WHERE t.ticketNumber = :number")
    Optional<Ticket> findTicketWithTicketNumber (@Param("number") Long number);

}