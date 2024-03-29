package fr.alexandreklotz.quickdesk.repository;

import fr.alexandreklotz.quickdesk.model.TicketPriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketPriorityRepository extends JpaRepository<TicketPriority, UUID> {

    @Query("FROM TicketPriority t WHERE t.isDefault = :defvalue")
    Optional<TicketPriority> findDefaultTicketPriorityValue(@Param("defvalue")boolean defvalue);

    @Transactional
    @Modifying
    @Query("UPDATE TicketPriority t SET t.isDefault = true WHERE t.id = :priorityid")
    void setDefaultTicketPriority(@Param("priorityid") UUID priorityid);

    @Query("FROM TicketPriority t WHERE t.name = :priorityname")
    Optional<TicketPriority> findTicketPriorityWithName(@Param("priorityname") String priorityname);
}
