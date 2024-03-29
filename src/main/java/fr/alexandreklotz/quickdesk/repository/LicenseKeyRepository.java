package fr.alexandreklotz.quickdesk.repository;

import fr.alexandreklotz.quickdesk.model.LicenseKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LicenseKeyRepository extends JpaRepository<LicenseKey, UUID> {

    @Query("FROM LicenseKey l WHERE l.licenseSerial = :key")
    Optional<LicenseKey> getLicenseByLicenseKey(@Param("key")String key);
}
