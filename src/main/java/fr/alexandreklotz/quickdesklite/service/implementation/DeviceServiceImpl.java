package fr.alexandreklotz.quickdesklite.service.implementation;

import fr.alexandreklotz.quickdesklite.error.DeviceException;
import fr.alexandreklotz.quickdesklite.error.UtilisateurException;
import fr.alexandreklotz.quickdesklite.model.Device;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.DeviceRepository;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import fr.alexandreklotz.quickdesklite.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public Device getDeviceById(UUID deviceId) throws DeviceException {
        return deviceRepository.findById(deviceId).orElseThrow(()
        -> new DeviceException(deviceId + "doesn't match any existing device in the database."));
    }

    @Override
    public Device createDevice(Device device) throws UtilisateurException {
        if(device.getDeviceUtilisateur() != null){
            Optional<Utilisateur> deviceUser = utilisateurRepository.findById(device.getDeviceUtilisateur().getId());
            if(device.getDeviceUtilisateur() != null && !deviceUser.isPresent()){
                throw new UtilisateurException("The user assigned to this device doesn't exist.");
            }
            deviceUser.get().setDevice(device);
            utilisateurRepository.saveAndFlush(deviceUser.get());
        }
        deviceRepository.saveAndFlush(device);
        return device;
    }

    @Override
    public Device updateDevice(Device device) throws DeviceException, UtilisateurException {
        Optional<Device> devBdd = deviceRepository.findById(device.getId());
        if(!devBdd.isPresent()){
            throw new DeviceException("The device you're trying to update doesn't exist.");
        }

        if(device.getDeviceManufacturer() != null){
            devBdd.get().setDeviceManufacturer(device.getDeviceManufacturer());
        }

        if(device.getDeviceDescription() != null){
            devBdd.get().setDeviceDescription(device.getDeviceDescription());
        }

        if(device.getDeviceName() != null){
            devBdd.get().setDeviceName(device.getDeviceName());
        }

        if(device.getDeviceSerialNbr() != null){
            devBdd.get().setDeviceSerialNbr(device.getDeviceSerialNbr());
        }

        if(device.getDeviceUtilisateur() != null){
            Optional<Utilisateur> userDev = utilisateurRepository.findById(device.getDeviceUtilisateur().getId());
            if(!userDev.isPresent()){
                throw new UtilisateurException("The user you're trying to assign to this device doesn't exist.");
            }
            devBdd.get().setDeviceUtilisateur(userDev.get());
        }

        deviceRepository.saveAndFlush(devBdd.get());
        return devBdd.get();
    }

    @Override
    public void deleteDevice(Device device) {
        deviceRepository.delete(device);
    }
}
