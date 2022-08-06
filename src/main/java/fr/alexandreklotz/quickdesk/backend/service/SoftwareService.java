package fr.alexandreklotz.quickdesk.backend.service;

import fr.alexandreklotz.quickdesk.backend.error.ContractException;
import fr.alexandreklotz.quickdesk.backend.error.LicenseKeyException;
import fr.alexandreklotz.quickdesk.backend.error.SoftwareException;
import fr.alexandreklotz.quickdesk.backend.model.Software;

import java.util.List;
import java.util.UUID;

public interface SoftwareService {

    public List<Software> getAllSoftware();

    public Software getSoftwareById(UUID softwareId) throws SoftwareException;

    public Software getSoftwareByName(String name) throws SoftwareException;

    public Software createSoftware(Software software) throws LicenseKeyException, ContractException;

    public Software updateSoftware(Software software) throws SoftwareException, LicenseKeyException, ContractException;

    public void deleteSoftwareById(UUID softId);
}
