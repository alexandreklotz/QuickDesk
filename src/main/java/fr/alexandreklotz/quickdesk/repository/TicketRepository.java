package fr.alexandreklotz.quickdesk.repository;

import fr.alexandreklotz.quickdesk.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    @Query("FROM Ticket t WHERE t.assignedAdmin = :adminId")
    List<Ticket> getAdminAssignedTickets (@Param("adminId")UUID adminId);

    @Query(value = "SELECT max(ticketNumber) FROM Ticket")
    Long getLatestTicketNumber();

    @Query("FROM Ticket t WHERE t.ticketNumber = :number")
    Optional<Ticket> findTicketWithTicketNumber (@Param("number") Long number);

    @Query("FROM Ticket WHERE editableTicket = :isclosed")
    List<Ticket> getClosedTickets (@Param("isclosed")boolean isclosed);

}