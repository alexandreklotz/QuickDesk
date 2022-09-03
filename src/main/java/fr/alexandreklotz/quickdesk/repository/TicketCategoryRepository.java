package fr.alexandreklotz.quickdesk.repository;

import fr.alexandreklotz.quickdesk.model.TicketCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketCategoryRepository extends JpaRepository<TicketCategory, UUID> {

    @Query("FROM TicketCategory t WHERE t.isDefault = :defvalue")
    Optional<TicketCategory> findDefaultTicketCategory(@Param("defvalue")boolean defvalue);

    @Transactional
    @Modifying
    @Query("UPDATE TicketCategory t SET t.isDefault = true WHERE t.id = :catid")
    void setDefaultTicketCategory(@Param("catid") UUID catid);

    @Query("FROM TicketCategory t WHERE t.name = :catname")
    Optional<TicketCategory> findTicketCategoryByName(@Param("catname")String catname);
}
