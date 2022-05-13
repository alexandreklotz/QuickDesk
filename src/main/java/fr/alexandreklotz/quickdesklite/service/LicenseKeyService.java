package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.model.LicenseKey;

import java.util.List;

public interface LicenseKeyService {

    public List<LicenseKey> getAllLicenses();

    public LicenseKey getSpecifiedLicenseKey(LicenseKey licenseKey);

    public LicenseKey createLicenseKey(LicenseKey licenseKey);

    public LicenseKey updateLicenseKey(LicenseKey licenseKey);

    public void deleteLicenseKey(LicenseKey licenseKey);
}
