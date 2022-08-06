package fr.alexandreklotz.quickdesk.backend.service;

import fr.alexandreklotz.quickdesk.backend.error.DeviceException;
import fr.alexandreklotz.quickdesk.backend.error.UtilisateurException;
import fr.alexandreklotz.quickdesk.backend.model.Device;

import java.util.List;
import java.util.UUID;

public interface DeviceService {

    public List<Device> getAllDevices();

    public Device getDeviceById(UUID deviceId) throws DeviceException;

    public Device getDeviceByName(String devName) throws DeviceException;

    public Device createDevice(Device device) throws UtilisateurException;

    public Device updateDevice(Device device) throws DeviceException, UtilisateurException;

    public void deleteDeviceById(UUID devId);
}
