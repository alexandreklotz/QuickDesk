package fr.alexandreklotz.quickdesklite.controller.admin;

import fr.alexandreklotz.quickdesklite.error.ContractException;
import fr.alexandreklotz.quickdesklite.error.ContractorException;
import fr.alexandreklotz.quickdesklite.error.SoftwareException;
import fr.alexandreklotz.quickdesklite.model.Contract;
import fr.alexandreklotz.quickdesklite.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class ContractController {

    private ContractService contractService;

    @Autowired
    ContractController(ContractService contractService){
        this.contractService = contractService;
    }

    //////////////////
    // REST METHODS //
    //////////////////

    @GetMapping("/admin/contract/all")
    public List<Contract> getAllContracts(){
        return contractService.getAllContracts();
    }

    @GetMapping("/admin/contract/{contractId}")
    public Contract getContractById(@PathVariable UUID contractId) throws ContractException {
        return contractService.getContractById(contractId);
    }

    @GetMapping("/admin/contract/{contractNbr}")
    public Contract getContractByContractNumber(@PathVariable String ctrNbr) throws ContractException {
        return contractService.getContractByContractNumber(ctrNbr);
    }

    @PostMapping("/admin/contract/new")
    public Contract createContract(@RequestBody Contract contract) throws ContractorException, SoftwareException {
        return contractService.createContract(contract);
    }

    @PutMapping("/admin/contract/update")
    public Contract updateContract(@RequestBody Contract contract) throws ContractException, ContractorException, SoftwareException {
        return contractService.updateContract(contract);
    }

    @DeleteMapping("/admin/contract/delete")
    public void deleteContract(@RequestBody Contract contract){
        contractService.deleteContract(contract);
    }
}
