package fr.alexandreklotz.quickdesk.dao;

import fr.alexandreklotz.quickdesk.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionDao extends JpaRepository<Permission, Integer> {
}
