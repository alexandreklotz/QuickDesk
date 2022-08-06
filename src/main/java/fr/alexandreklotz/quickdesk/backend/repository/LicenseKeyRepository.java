package fr.alexandreklotz.quickdesk.backend.repository;

import fr.alexandreklotz.quickdesk.backend.model.LicenseKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LicenseKeyRepository extends JpaRepository<LicenseKey, UUID> {

    @Query("FROM LicenseKey l WHERE l.lkey = :key")
    Optional<LicenseKey> getLicenseByLicenseKey(@Param("key")String key);
}
