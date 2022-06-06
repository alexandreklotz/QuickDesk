package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.model.Software;

import java.util.List;
import java.util.UUID;

public interface SoftwareService {

    public List<Software> getAllSoftware();

    public Software getSpecifiedSoftware(UUID softwareId);

    public Software createSoftware(Software software);

    public Software updateSoftware(Software software);

    public void deleteSoftware(Software software);
}
