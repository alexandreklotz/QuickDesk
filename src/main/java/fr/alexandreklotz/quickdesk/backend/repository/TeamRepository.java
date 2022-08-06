package fr.alexandreklotz.quickdesk.backend.repository;

import fr.alexandreklotz.quickdesk.backend.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<Team, UUID> {

    @Query("FROM Team t WHERE t.teamName = :name")
    Optional<Team> getTeamByName(@Param("name")String name);
}
