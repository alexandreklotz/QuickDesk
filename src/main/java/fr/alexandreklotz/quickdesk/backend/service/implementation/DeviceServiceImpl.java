package fr.alexandreklotz.quickdesk.backend.service.implementation;

import fr.alexandreklotz.quickdesk.backend.error.ContractException;
import fr.alexandreklotz.quickdesk.backend.error.DeviceException;
import fr.alexandreklotz.quickdesk.backend.error.UtilisateurException;
import fr.alexandreklotz.quickdesk.backend.model.Contract;
import fr.alexandreklotz.quickdesk.backend.model.Device;
import fr.alexandreklotz.quickdesk.backend.model.Utilisateur;
import fr.alexandreklotz.quickdesk.backend.repository.ContractRepository;
import fr.alexandreklotz.quickdesk.backend.repository.DeviceRepository;
import fr.alexandreklotz.quickdesk.backend.repository.UtilisateurRepository;
import fr.alexandreklotz.quickdesk.backend.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeviceServiceImpl implements DeviceService {

    private DeviceRepository deviceRepository;
    private ContractRepository contractRepository;
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    DeviceServiceImpl(DeviceRepository deviceRepository, UtilisateurRepository utilisateurRepository, ContractRepository contractRepository){
        this.deviceRepository = deviceRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.contractRepository = contractRepository;
    }

    @Override
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    @Override
    public Device getDeviceById(UUID deviceId) throws DeviceException {
        return deviceRepository.findById(deviceId).orElseThrow(()
        -> new DeviceException("ERROR : " + deviceId + "doesn't match any existing device in the database."));
    }

    @Override
    public Device getDeviceByName(String devName) throws DeviceException {
        return deviceRepository.findDeviceByName(devName).orElseThrow(()
        -> new DeviceException("ERROR : The device " + devName + " doesn't exist."));
    }

    @Override
    public Device createDevice(Device device) throws UtilisateurException, ContractException {

        if(device.getUtilisateur() != null){
            Optional<Utilisateur> deviceUser = utilisateurRepository.findById(device.getUtilisateur().getId());
            if(device.getUtilisateur() != null && deviceUser.isEmpty()){
                throw new UtilisateurException("ERROR : The user assigned to this device doesn't exist.");
            }
            deviceUser.get().setDevice(device);
        }

        if(device.getContract() != null){
            Optional<Contract> contract = contractRepository.findById(device.getContract().getId());
            if(contract.isEmpty()){
                throw new ContractException("ERROR : The contrac " + device.getContract().getId() + " doesn't exist.");
            }
            device.setContract(contract.get());
        }


        device.setDeviceCreated(LocalDateTime.now());

        deviceRepository.saveAndFlush(device);
        return device;
    }

    @Override
    public Device updateDevice(Device device) throws DeviceException, UtilisateurException, ContractException {

        Optional<Device> devBdd = deviceRepository.findById(device.getId());
        if(devBdd.isEmpty()){
            throw new DeviceException("ERROR : The device you're trying to update doesn't exist.");
        }

        if(device.getUtilisateur() != null){
            Optional<Utilisateur> userDev = utilisateurRepository.findById(device.getUtilisateur().getId());
            if(userDev.isEmpty()){
                throw new UtilisateurException("ERROR : The user " + device.getUtilisateur().getId() + " you're trying to assign to this device doesn't exist.");
            }
            userDev.get().setDevice(device);
        }

        if(device.getContract() != null){
            Optional<Contract> contract = contractRepository.findById(device.getContract().getId());
            if(contract.isEmpty()){
                throw new ContractException("ERROR : The contrac " + device.getContract().getId() + " doesn't exist.");
            }
            device.setContract(contract.get());
        }

        deviceRepository.saveAndFlush(device);
        return device;
    }

    @Override
    public void deleteDeviceById(UUID devId) throws DeviceException {
        deviceRepository.deleteById(devId);
    }
}
