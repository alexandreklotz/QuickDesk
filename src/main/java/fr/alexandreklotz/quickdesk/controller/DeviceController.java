package fr.alexandreklotz.quickdesk.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.dao.ContractDao;
import fr.alexandreklotz.quickdesk.dao.DeviceDao;
import fr.alexandreklotz.quickdesk.dao.UserDao;
import fr.alexandreklotz.quickdesk.model.Contract;
import fr.alexandreklotz.quickdesk.model.Device;
import fr.alexandreklotz.quickdesk.model.User;
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
    private UserDao userDao;
    private ContractDao contractDao;

    @Autowired
    DeviceController (DeviceDao deviceDao, UserDao userDao, ContractDao contractDao) {
        this.deviceDao = deviceDao;
        this.userDao = userDao;
        this.contractDao = contractDao;
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

        Contract devCtr = device.getContract();
        Optional<Contract> contractBdd = contractDao.findById(devCtr.getContractId());
        if (contractBdd.isPresent()) {
            device.setContract(device.getContract());
        } else if (contractBdd.isEmpty()) {
            device.setContract(null);
        }

        User devUsr = device.getUser();
        Optional<User> userBdd = userDao.findById(devUsr.getUserId());
        if (userBdd.isPresent()) {
            device.setUser(device.getUser());
        }

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
