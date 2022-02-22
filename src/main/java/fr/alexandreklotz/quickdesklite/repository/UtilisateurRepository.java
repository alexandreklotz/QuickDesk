package fr.alexandreklotz.quickdesklite.repository;

import fr.alexandreklotz.quickdesklite.model.Ticket;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, UUID> {

    @Query("FROM Utilisateur u WHERE u.utilLogin = :login")
    Optional<Utilisateur> findUserWithLogin (String login);

    /*@Query("FROM Ticket t WHERE t.assignedAdmin = :userid")
    Optional<List<Ticket>> findTicketsAssigned (UUID userid);

    @Query("FROM Ticket t WHERE t.utilisateur = :userid")
    Optional<List<Ticket>> findTicketsOpened (UUID userid);*/
}
