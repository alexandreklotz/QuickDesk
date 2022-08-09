package fr.alexandreklotz.quickdesk.backend.controller.admin;

import fr.alexandreklotz.quickdesk.backend.error.DeviceException;
import fr.alexandreklotz.quickdesk.backend.error.UtilisateurException;
import fr.alexandreklotz.quickdesk.backend.model.Device;
import fr.alexandreklotz.quickdesk.backend.service.DeviceService;
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

    @GetMapping("/admin/device/id/{deviceId}")
    public Device getDeviceById(@PathVariable UUID deviceId) throws DeviceException {
        return deviceService.getDeviceById(deviceId);
    }

    @GetMapping("/admin/device/name/{devName}")
    public Device getDeviceByName(@PathVariable String devName) throws DeviceException {
        return deviceService.getDeviceByName(devName);
    }

    @PostMapping("/admin/device/create")
    public Device createDevice(@RequestBody Device device) throws UtilisateurException {
        return deviceService.createDevice(device);
    }

    @PutMapping("/admin/device/update")
    public Device updateDevice(@RequestBody Device device) throws UtilisateurException, DeviceException {
        return deviceService.updateDevice(device);
    }

    @DeleteMapping("/admin/device/id/{devId}/delete")
    public void deleteDevice(@PathVariable UUID devId) {
        deviceService.deleteDeviceById(devId);
    }
}
