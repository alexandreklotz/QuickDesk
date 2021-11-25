package fr.alexandreklotz.quickdesk.dao;

import fr.alexandreklotz.quickdesk.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamDao extends JpaRepository<Team, Integer> {

}