package fr.alexandreklotz.quickdesklite.repository;

import fr.alexandreklotz.quickdesklite.model.TicketCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketCategoryRepository extends JpaRepository<TicketCategory, Long> {

    @Query("FROM TicketCategory t WHERE t.isDefault = :defvalue")
    Optional<TicketCategory> findDefaultTicketCategory(@Param("defvalue")boolean defvalue);

    @Modifying
    @Query("UPDATE TicketCategory t SET t.isDefault = true WHERE t.id = :catid")
    void setDefaultTicketCategory(@Param("catid")Long catid);
}
