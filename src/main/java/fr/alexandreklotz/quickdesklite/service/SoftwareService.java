package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.error.ContractException;
import fr.alexandreklotz.quickdesklite.error.LicenseKeyException;
import fr.alexandreklotz.quickdesklite.error.SoftwareException;
import fr.alexandreklotz.quickdesklite.model.Software;

import java.util.List;
import java.util.UUID;

public interface SoftwareService {

    public List<Software> getAllSoftware();

    public Software getSpecifiedSoftware(UUID softwareId) throws SoftwareException;

    public Software createSoftware(Software software) throws LicenseKeyException, ContractException;

    public Software updateSoftware(Software software) throws SoftwareException, LicenseKeyException, ContractException;

    public void deleteSoftware(Software software);
}
