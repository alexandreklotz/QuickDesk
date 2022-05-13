package fr.alexandreklotz.quickdesklite.repository;

import fr.alexandreklotz.quickdesklite.model.TicketPriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketPriorityRepository extends JpaRepository<TicketPriority, Long> {

    @Query("FROM TicketPriority t WHERE t.isDefault = :defvalue")
    Optional<TicketPriority> findDefaultTicketPriorityValue(@Param("defvalue")boolean defvalue);

    @Modifying
    @Query("UPDATE TicketPriority t SET t.isDefault = true WHERE t.id = :priorityid")
    void setDefaultTicketPriority(@Param("priorityid")Long priorityid);
}
