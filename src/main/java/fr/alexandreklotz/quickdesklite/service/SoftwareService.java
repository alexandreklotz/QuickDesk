package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.model.Software;

import java.util.List;

public interface SoftwareService {

    public List<Software> getAllSoftware();

    public Software getSpecifiedSoftware(Software software);

    public Software addSoftware(Software software);

    public Software updateSoftware(Software software);

    public void deleteSoftware(Software software);
}
