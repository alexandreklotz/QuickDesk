package fr.alexandreklotz.quickdesk.dao;

import fr.alexandreklotz.quickdesk.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketDao extends JpaRepository<Ticket, Integer> {

}