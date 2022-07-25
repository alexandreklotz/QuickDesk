package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.error.LicenseKeyException;
import fr.alexandreklotz.quickdesklite.error.SoftwareException;
import fr.alexandreklotz.quickdesklite.model.LicenseKey;

import java.util.List;
import java.util.UUID;

public interface LicenseKeyService {

    public List<LicenseKey> getAllLicenses();

    public LicenseKey getLicensekeyById(UUID licenseId) throws LicenseKeyException;

    public LicenseKey createLicenseKey(LicenseKey licenseKey) throws SoftwareException;

    public LicenseKey updateLicenseKey(LicenseKey licenseKey) throws LicenseKeyException, SoftwareException;

    public void deleteLicenseKey(LicenseKey licenseKey);
}
