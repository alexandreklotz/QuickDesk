package fr.alexandreklotz.quickdesk.service;

import fr.alexandreklotz.quickdesk.error.ContractException;
import fr.alexandreklotz.quickdesk.error.DeviceException;
import fr.alexandreklotz.quickdesk.error.UtilisateurException;
import fr.alexandreklotz.quickdesk.model.Device;

import java.util.List;
import java.util.UUID;

public interface DeviceService {

    public List<Device> getAllDevices();

    public Device getDeviceById(UUID deviceId) throws DeviceException;

    public Device getDeviceByName(String devName) throws DeviceException;

    public Device createDevice(Device device) throws UtilisateurException, ContractException;

    public Device updateDevice(Device device) throws DeviceException, UtilisateurException, ContractException;

    public void deleteDeviceById(UUID devId) throws DeviceException;
}
