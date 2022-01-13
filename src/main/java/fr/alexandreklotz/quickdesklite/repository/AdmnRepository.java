package fr.alexandreklotz.quickdesklite.repository;

import fr.alexandreklotz.quickdesklite.model.Admn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdmnRepository extends JpaRepository<Admn, UUID> {
}
