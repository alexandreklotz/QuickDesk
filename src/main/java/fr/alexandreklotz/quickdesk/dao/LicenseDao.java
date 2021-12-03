package fr.alexandreklotz.quickdesk.dao;

import fr.alexandreklotz.quickdesk.model.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseDao extends JpaRepository<License, Integer> {
}
