package fr.alexandreklotz.quickdesklite.controller.admin;

import fr.alexandreklotz.quickdesklite.error.DeviceException;
import fr.alexandreklotz.quickdesklite.error.UtilisateurException;
import fr.alexandreklotz.quickdesklite.model.Device;
import fr.alexandreklotz.quickdesklite.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class DeviceController {

    private DeviceService deviceService;

    @Autowired
    DeviceController(DeviceService deviceService){
        this.deviceService = deviceService;
    }

    //////////////////
    // REST METHODS //
    //////////////////

    @GetMapping("/admin/device/all")
    public List<Device> getAllDevices(){
        return deviceService.getAllDevices();
    }

    @GetMapping("/admin/device/{deviceId}")
    public Device getDeviceById(@PathVariable UUID deviceId) throws DeviceException {
        return deviceService.getDeviceById(deviceId);
    }

    @PostMapping("/admin/device/new")
    public Device createDevice(@RequestBody Device device) throws UtilisateurException {
        return deviceService.createDevice(device);
    }

    @PostMapping("/admin/device/update")
    public Device updateDevice(@RequestBody Device device) throws UtilisateurException, DeviceException {
        return deviceService.updateDevice(device);
    }

    @DeleteMapping("/admin/device/delete")
    public void deleteDevice(@RequestBody Device device) {
        deviceService.deleteDevice(device);
    }
}
