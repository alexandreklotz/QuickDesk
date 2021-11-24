package fr.alexandreklotz.quickdesk.dao;

import fr.alexandreklotz.quickdesk.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketDao extends JpaRepository<Ticket, Integer> {

}