package fr.alexandreklotz.quickdesklite.repository;

import fr.alexandreklotz.quickdesklite.model.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketStatusRepository extends JpaRepository<TicketStatus, Long> {

    @Query("FROM TicketStatus t WHERE t.isDefault = :defvalue")
    Optional<TicketStatus> findDefaultTicketStatusValue(@Param("defvalue") boolean defvalue);

    @Modifying
    @Query("UPDATE TicketStatus t SET t.isDefault = true WHERE t.id = :statusid")
    void setDefaultTicketStatus(@Param("statusid")Long statusid);

}
