package fr.alexandreklotz.quickdesk.dao;

import fr.alexandreklotz.quickdesk.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamDao extends JpaRepository<Team, Integer> {

}