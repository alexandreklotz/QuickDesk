package fr.alexandreklotz.quickdesk.repository;

import fr.alexandreklotz.quickdesk.model.Software;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SoftwareRepository extends JpaRepository<Software, UUID> {

    @Query("FROM Software s WHERE s.name = :name")
    Optional<Software> getSoftwareByName(@Param("name")String name);
}
