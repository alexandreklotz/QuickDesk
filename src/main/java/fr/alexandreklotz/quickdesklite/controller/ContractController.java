package fr.alexandreklotz.quickdesklite.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.model.Contract;
import fr.alexandreklotz.quickdesklite.repository.ContractRepository;
import fr.alexandreklotz.quickdesklite.repository.ContractorRepository;
import fr.alexandreklotz.quickdesklite.repository.DeviceRepository;
import fr.alexandreklotz.quickdesklite.repository.SoftwareRepository;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class ContractController {

    private ContractRepository contractRepository;
    private ContractorRepository contractorRepository;
    private SoftwareRepository softwareRepository;
    private DeviceRepository deviceRepository;

    @Autowired
    ContractController(ContractRepository contractRepository, ContractorRepository contractorRepository, SoftwareRepository softwareRepository, DeviceRepository deviceRepository){
        this.contractRepository = contractRepository;
        this.contractorRepository = contractorRepository;
        this.softwareRepository = softwareRepository;
        this.deviceRepository = deviceRepository;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.ContractView.class)
    @GetMapping("/admin/contract/all")
    public ResponseEntity<List<Contract>> getAllContracts(){
        return ResponseEntity.ok(contractRepository.findAll());
    }

    @JsonView(CustomJsonView.ContractView.class)
    @GetMapping("/admin/contract/{contractid}")
    public ResponseEntity<Contract> getSpecifiedContract(@PathVariable Long contractid){
        Optional<Contract> ctrBdd = contractRepository.findById(contractid);
        if(ctrBdd.isPresent()){
            return ResponseEntity.ok(ctrBdd.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
