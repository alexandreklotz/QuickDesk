package fr.alexandreklotz.quickdesklite.repository;

import fr.alexandreklotz.quickdesklite.model.TicketQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketQueueRepository extends JpaRepository<TicketQueue, UUID> {

    @Query("FROM TicketQueue t WHERE t.isDefault = :defvalue")
    Optional<TicketQueue> findDefaultTicketQueueValue(@Param("defvalue")boolean defvalue);

    @Modifying
    @Query("UPDATE TicketQueue t SET t.isDefault = true WHERE t.id = :queueid")
    void setDefaultTicketQueue(@Param("queueid") UUID queueid);
}
