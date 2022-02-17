package fr.alexandreklotz.quickdesklite.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.model.Device;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.DeviceRepository;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class DeviceController {

    private DeviceRepository deviceRepository;
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    DeviceController (DeviceRepository deviceRepository, UtilisateurRepository utilisateurRepository){
        this.deviceRepository = deviceRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.DeviceView.class)
    @GetMapping("/admin/devices/all")
    public ResponseEntity<List<Device>> getAllDevices(){
        return ResponseEntity.ok(deviceRepository.findAll());
    }

    @JsonView(CustomJsonView.DeviceView.class)
    @GetMapping("/admin/devices/{deviceid}")
    public ResponseEntity<Device> getSpecifiedDevice(@PathVariable Long deviceid){

        Optional<Device> deviceBdd = deviceRepository.findById(deviceid);

        //The next if block can be replaced by this expression : return deviceBdd.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
        if(deviceBdd.isPresent()){
            return ResponseEntity.ok(deviceBdd.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @JsonView(CustomJsonView.DeviceView.class)
    @PostMapping("/admin/device/new")
    public ResponseEntity<String> newDevice(@RequestBody Device device){

        //We check if a value is specified in the DeviceUtilisateur value. We check if it exists by using the repository linked to this entity. If it does, we assign it to the device.
        if(device.getDeviceUtilisateur() != null){
                Optional<Utilisateur> userBdd = utilisateurRepository.findById(device.getDeviceUtilisateur().getId());
                if(userBdd.isPresent()){
                    userBdd.get().setDevice(device);
                }
        }

        device.setDeviceCreated(LocalDateTime.now());
        deviceRepository.saveAndFlush(device);
        return ResponseEntity.ok(device.getDeviceName() + " successfully created.");
    }

    @JsonView(CustomJsonView.DeviceView.class)
    @PutMapping("/admin/device/{deviceid}/update")
    public ResponseEntity<String> updateDevice(@PathVariable Long deviceid,
                                               @RequestBody Device device){

    //We create an optional to check if the specified device exists. If it does, we will check each field of the put request and update them if they need to.
        Optional<Device> deviceBdd = deviceRepository.findById(deviceid);
        if(deviceBdd.isPresent()){

            if(device.getDeviceName() != null){
                deviceBdd.get().setDeviceName(device.getDeviceName());
            }
            if(device.getDeviceDescription() != null){
                deviceBdd.get().setDeviceDescription(device.getDeviceDescription());
            }
            if(device.getDeviceManufacturer() != null){
                deviceBdd.get().setDeviceManufacturer(device.getDeviceManufacturer());
            }
            if(device.getDeviceUtilisateur() != null){
                    Optional<Utilisateur> userBdd = utilisateurRepository.findById(device.getDeviceUtilisateur().getId());
                    if(userBdd.isPresent()){
                        userBdd.get().setDevice(deviceBdd.get());
                    }
            }
            deviceRepository.save(deviceBdd.get());
            return ResponseEntity.ok().body(deviceBdd.get().getDeviceName() + " has been successfully updated.");
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @JsonView(CustomJsonView.DeviceView.class)
    @DeleteMapping("/admin/devices/{deviceid}/delete")
    public String deleteDevice(@PathVariable Long deviceid){

        //We check if the specified device exists by using DeviceRepository, and if it does we then proceed with the deletion.
        Optional<Device> deviceBdd = deviceRepository.findById(deviceid);
        if(deviceBdd.isPresent()){
            String devDel = deviceBdd.get().getDeviceName() + " has been deleted.";
            deviceRepository.deleteById(deviceid);
            return devDel;
        } else {
            return "The specified device doesn't exist.";
        }
    }
}
