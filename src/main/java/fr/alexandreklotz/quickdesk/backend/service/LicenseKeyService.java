package fr.alexandreklotz.quickdesk.backend.service;

import fr.alexandreklotz.quickdesk.backend.error.LicenseKeyException;
import fr.alexandreklotz.quickdesk.backend.error.SoftwareException;
import fr.alexandreklotz.quickdesk.backend.model.LicenseKey;

import java.util.List;
import java.util.UUID;

public interface LicenseKeyService {

    public List<LicenseKey> getAllLicenses();

    public LicenseKey getLicensekeyById(UUID licenseId) throws LicenseKeyException;

    public LicenseKey getLicenseKeyByKey(String licKey) throws LicenseKeyException;

    public LicenseKey createLicenseKey(LicenseKey licenseKey) throws SoftwareException;

    public LicenseKey updateLicenseKey(LicenseKey licenseKey) throws LicenseKeyException, SoftwareException;

    public void deleteLicenseKeyById(UUID licId);
}
