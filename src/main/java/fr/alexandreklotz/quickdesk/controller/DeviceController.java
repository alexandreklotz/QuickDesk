package fr.alexandreklotz.quickdesk.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.dao.DeviceDao;
import fr.alexandreklotz.quickdesk.model.Device;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
//@RequestMapping("/devices")
public class DeviceController {

    private DeviceDao deviceDao;

    @Autowired
    DeviceController (DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.DeviceView.class)
    @GetMapping("/devices/all")
    public ResponseEntity<List<Device>> getAllDevices(){
        return ResponseEntity.ok(deviceDao.findAll());
    }

    @JsonView(CustomJsonView.DeviceView.class)
    @PostMapping("/devices/new")
    public void newDevice(@RequestBody Device device){

        device.setDeviceDesc(device.getDeviceDesc());
        device.setDeviceManufacturer(device.getDeviceManufacturer());
        device.setContract(device.getContract());
        device.setDeviceSerialNbr(device.getDeviceSerialNbr());
        device.setUser(device.getUser());

        deviceDao.saveAndFlush(device);
    }

    /*@JsonView(CustomJsonView.DeviceView.class)
    @PatchMapping("/devices/update/{deviceId}")*/

    @JsonView(CustomJsonView.DeviceView.class)
    @DeleteMapping("/devices/delete/{deviceId}")
    public String deleteDevice (@PathVariable int deviceId) {

        Optional<Device> deviceBdd = deviceDao.findById(deviceId);

        if (deviceBdd.isPresent()){
            String dvName = deviceBdd.get().getDeviceName();
            deviceDao.deleteById(deviceId);
            return "The device " + dvName + " has been deleted.";
        }
        return "The specified device doesn't exist.";
    }
}
