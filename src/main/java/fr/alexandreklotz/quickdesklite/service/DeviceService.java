package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.model.Device;

import java.util.List;

public interface DeviceService {

    public List<Device> getAllDevices();

    public Device getSpecificDevice(Device device);

    public Device createDevice(Device device);

    public Device updateDevice(Device device);

    public void deleteDevice(Device device);
}
