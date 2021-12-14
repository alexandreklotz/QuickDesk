package fr.alexandreklotz.quickdesk.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.dao.*;
import fr.alexandreklotz.quickdesk.model.*;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin
public class ContractController {

    private ContractDao contractDao;
    private ContractorDao contractorDao;
    private DeviceDao deviceDao;
    private LicenseDao licenseDao;
    private SoftwareDao softwareDao;

    @Autowired
    ContractController (ContractDao contractDao, ContractorDao contractorDao, DeviceDao deviceDao, LicenseDao licenseDao, SoftwareDao softwareDao) {
        this.contractDao = contractDao;
        this.contractorDao = contractorDao;
        this.deviceDao = deviceDao;
        this.licenseDao = licenseDao;
        this.softwareDao = softwareDao;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.ContractView.class)
    @GetMapping("/contracts/all")
    public ResponseEntity<List<Contract>> getAllContracts(){
        return ResponseEntity.ok(contractDao.findAll());
    }

    @JsonView(CustomJsonView.ContractView.class)
    @PostMapping("/contracts/new")
    public void newContractCreation (@RequestBody Contract contract){

        Contractor ctrContr = contract.getContractor();
        Optional<Contractor> contractBdd = contractorDao.findById(ctrContr.getContractorId());
        if (contractBdd.isPresent()){
            contract.setContractor(contract.getContractor());
        } else if (contractBdd.isEmpty()) {
            contract.setContractor(null);
        }

        List<Device> ctrDev = new ArrayList<>();
        for (Device device : contract.getDevice()) {
            Optional<Device> deviceBdd = deviceDao.findById(device.getDeviceId());
            if (deviceBdd.isPresent()) {
                ctrDev.add(device);
                contract.setDevice(ctrDev);
            } else if (deviceBdd.isEmpty()) {
                //We don't do anything.
            }

        }

        List<License> ctrLic = new ArrayList<>();
        for (License license : contract.getLicense()) {
            Optional<License> licenseBdd = licenseDao.findById(license.getLicenseId());
            if (licenseBdd.isPresent()) {
                ctrLic.add(license);
                contract.setLicense(ctrLic);
            } else if (licenseBdd.isEmpty()) {
                //No action
            }

        }

        List<Software> ctrSoft = new ArrayList<>();
        for (Software software : contract.getSoftware()) {
            Optional<Software> softwareBdd = softwareDao.findById(software.getSoftwareId());
            if (softwareBdd.isPresent()) {
                ctrSoft.add(software);
                contract.setSoftware(ctrSoft);
            } else if (softwareBdd.isEmpty()) {
                //No action
            }
        }

        contractDao.saveAndFlush(contract);
    }

    /*@JsonView(CustomJsonView.ContractView.class)
    @PatchMapping("/contracts/update/{contractId}")*/

    @JsonView(CustomJsonView.ContractView.class)
    @DeleteMapping("/contracts/delete/{contractId}")
    public String deleteContract (@PathVariable int contractId){

        Optional<Contract> contractBdd = contractDao.findById(contractId);

        if (contractBdd.isPresent()){
            String ctrName = contractDao.getById(contractId).getContractName();
            String ctrNbr = contractDao.getById(contractId).getContractNumber();
            contractDao.deleteById(contractId);
            return "The contract " + ctrName + " N° " + ctrNbr + " has been deleted.";
        }
        return "The specified contract doesn't exist.";
    }
}
