package fr.alexandreklotz.quickdesk.backend.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.backend.error.ContractException;
import fr.alexandreklotz.quickdesk.backend.error.ContractorException;
import fr.alexandreklotz.quickdesk.backend.error.SoftwareException;
import fr.alexandreklotz.quickdesk.backend.model.Contract;
import fr.alexandreklotz.quickdesk.backend.service.ContractService;
import fr.alexandreklotz.quickdesk.backend.view.CustomJsonView;
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

    @JsonView(CustomJsonView.ContractView.class)
    @GetMapping("/admin/contract/all")
    public List<Contract> getAllContracts(){
        return contractService.getAllContracts();
    }

    @JsonView(CustomJsonView.ContractView.class)
    @GetMapping("/admin/contract/id/{contractId}")
    public Contract getContractById(@PathVariable UUID contractId) throws ContractException {
        return contractService.getContractById(contractId);
    }

    @JsonView(CustomJsonView.ContractView.class)
    @GetMapping("/admin/contract/number/{contractNbr}")
    public Contract getContractByContractNumber(@PathVariable String ctrNbr) throws ContractException {
        return contractService.getContractByContractNumber(ctrNbr);
    }

    @JsonView(CustomJsonView.ContractView.class)
    @PostMapping("/admin/contract/create")
    public Contract createContract(@RequestBody Contract contract) throws ContractorException, SoftwareException {
        return contractService.createContract(contract);
    }

    @JsonView(CustomJsonView.ContractView.class)
    @PutMapping("/admin/contract/update")
    public Contract updateContract(@RequestBody Contract contract) throws ContractException, ContractorException, SoftwareException {
        return contractService.updateContract(contract);
    }

    @JsonView(CustomJsonView.ContractView.class)
    @DeleteMapping("/admin/contract/id/{ctrId}/delete")
    public void deleteContract(@PathVariable UUID ctrId){
        contractService.deleteContractById(ctrId);
    }
}
