package fr.alexandreklotz.quickdesk.repository;

import fr.alexandreklotz.quickdesk.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, UUID> {

    @Query("FROM Utilisateur u WHERE u.utilLogin = :login")
    Optional<Utilisateur> findUserWithLogin (@Param("login")String login);

    @Query("FROM Utilisateur u JOIN FETCH u.role WHERE u.utilLogin = :login")
    Optional<Utilisateur> findUserLoginAndRoles (@Param("login")String login);

}
