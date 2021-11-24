package fr.alexandreklotz.quickdesk.dao;

import fr.alexandreklotz.quickdesk.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupDao extends JpaRepository<Group, Integer> {

}