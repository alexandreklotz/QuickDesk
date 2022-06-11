package fr.alexandreklotz.quickdesklite.repository;

import fr.alexandreklotz.quickdesklite.model.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketTypeRepository extends JpaRepository<TicketType, UUID> {

    @Query("FROM TicketType t WHERE t.isDefault = :defvalue")
    Optional<TicketType> findDefaultTicketTypeValue(@Param("defvalue")boolean defvalue);

    @Modifying
    @Query("UPDATE TicketType t SET t.isDefault = true WHERE t.id = :typeid")
    void setDefaultTicketType(@Param("typeid") UUID typeid);

    @Query("FROM TicketType t WHERE t.name = :typename")
    Optional<TicketType> findTicketTypeValueByName(@Param("typename")String typename);
}
