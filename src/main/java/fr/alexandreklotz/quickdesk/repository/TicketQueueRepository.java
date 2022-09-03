package fr.alexandreklotz.quickdesk.repository;

import fr.alexandreklotz.quickdesk.model.TicketQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketQueueRepository extends JpaRepository<TicketQueue, UUID> {

    @Query("FROM TicketQueue t WHERE t.isDefault = :defvalue")
    Optional<TicketQueue> findDefaultTicketQueueValue(@Param("defvalue")boolean defvalue);

    @Transactional
    @Modifying
    @Query("UPDATE TicketQueue t SET t.isDefault = true WHERE t.id = :queueid")
    void setDefaultTicketQueue(@Param("queueid") UUID queueid);

    @Query("FROM TicketQueue t WHERE t.name = :queuename")
    Optional<TicketQueue> findTicketQueueByName(@Param("queuename") String queuename);
}
