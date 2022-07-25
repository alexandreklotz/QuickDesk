package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.error.DeviceException;
import fr.alexandreklotz.quickdesklite.error.UtilisateurException;
import fr.alexandreklotz.quickdesklite.model.Device;

import java.util.List;
import java.util.UUID;

public interface DeviceService {

    public List<Device> getAllDevices();

    public Device getDeviceById(UUID deviceId) throws DeviceException;

    public Device createDevice(Device device) throws UtilisateurException;

    public Device updateDevice(Device device) throws DeviceException, UtilisateurException;

    public void deleteDevice(Device device);
}
