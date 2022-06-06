package fr.alexandreklotz.quickdesklite.service.implementation;

import fr.alexandreklotz.quickdesklite.model.Device;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.DeviceRepository;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import fr.alexandreklotz.quickdesklite.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService {

    private DeviceRepository deviceRepository;
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    DeviceServiceImpl(DeviceRepository deviceRepository, UtilisateurRepository utilisateurRepository){
        this.deviceRepository = deviceRepository;
        this.utilisateurRepository = utilisateurRepository;
    }


    @Override
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    @Override
    public Device getSpecificDevice(Device device) {
        Optional<Device> searchedDevice = deviceRepository.findById(device.getId());
        if(searchedDevice.isPresent()){
            return searchedDevice.get();
        } else {
            return null;
        }
    }

    @Override
    public Device createDevice(Device device) {
        if(device.getDeviceUtilisateur() != null){
            Optional<Utilisateur> deviceUser = utilisateurRepository.findById(device.getDeviceUtilisateur().getId());
            if(deviceUser.isPresent()){
                deviceUser.get().setDevice(device);
                utilisateurRepository.saveAndFlush(deviceUser.get());
            }
        }
        deviceRepository.saveAndFlush(device);
        return device;
    }

    @Override
    public Device updateDevice(Device device) {
        return null;
    }

    @Override
    public void deleteDevice(Device device) {
        deviceRepository.delete(device);
    }
}
