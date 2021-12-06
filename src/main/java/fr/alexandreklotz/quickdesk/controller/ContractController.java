package fr.alexandreklotz.quickdesk.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.dao.ContractDao;
import fr.alexandreklotz.quickdesk.model.Contract;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class ContractController {

    private ContractDao contractDao;

    @Autowired
    ContractController (ContractDao contractDao) {
        this.contractDao = contractDao;
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

        contract.setContractNumber(contract.getContractNumber());
        contract.setContractName(contract.getContractName());

        contract.setContractor(contract.getContractor());
        contract.setDevices(contract.getDevices());
        contract.setLicenses(contract.getLicenses());
        contract.setSoftware(contract.getSoftware());
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
