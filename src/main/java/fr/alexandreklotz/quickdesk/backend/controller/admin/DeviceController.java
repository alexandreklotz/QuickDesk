package fr.alexandreklotz.quickdesk.backend.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.backend.error.ContractException;
import fr.alexandreklotz.quickdesk.backend.error.DeviceException;
import fr.alexandreklotz.quickdesk.backend.error.UtilisateurException;
import fr.alexandreklotz.quickdesk.backend.model.Device;
import fr.alexandreklotz.quickdesk.backend.service.DeviceService;
import fr.alexandreklotz.quickdesk.backend.view.CustomJsonView;
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

    @JsonView(CustomJsonView.DeviceView.class)
    @GetMapping("/admin/device/all")
    public List<Device> getAllDevices(){
        return deviceService.getAllDevices();
    }

    @JsonView(CustomJsonView.DeviceView.class)
    @GetMapping("/admin/device/id/{deviceId}")
    public Device getDeviceById(@PathVariable UUID deviceId) throws DeviceException {
        return deviceService.getDeviceById(deviceId);
    }

    @JsonView(CustomJsonView.DeviceView.class)
    @GetMapping("/admin/device/name/{devName}")
    public Device getDeviceByName(@PathVariable String devName) throws DeviceException {
        return deviceService.getDeviceByName(devName);
    }

    @JsonView(CustomJsonView.DeviceView.class)
    @PostMapping("/admin/device/create")
    public Device createDevice(@RequestBody Device device) throws UtilisateurException, ContractException {
        return deviceService.createDevice(device);
    }

    @JsonView(CustomJsonView.DeviceView.class)
    @PutMapping("/admin/device/update")
    public Device updateDevice(@RequestBody Device device) throws UtilisateurException, DeviceException, ContractException {
        return deviceService.updateDevice(device);
    }

    @JsonView(CustomJsonView.DeviceView.class)
    @DeleteMapping("/admin/device/id/{devId}/delete")
    public void deleteDevice(@PathVariable UUID devId) throws DeviceException {
        deviceService.deleteDeviceById(devId);
    }
}
