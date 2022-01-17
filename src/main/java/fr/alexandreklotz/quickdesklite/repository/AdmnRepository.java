package fr.alexandreklotz.quickdesklite.repository;

import fr.alexandreklotz.quickdesklite.model.Admn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdmnRepository extends JpaRepository<Admn, UUID> {

    @Query("FROM Admn a WHERE a.admnLogin = :login")
    Optional<Admn> findAdmnWithLogin (String login);
}
