package fr.alexandreklotz.quickdesk.dao;

import fr.alexandreklotz.quickdesk.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Integer> {

}