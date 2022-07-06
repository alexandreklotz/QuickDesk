package fr.alexandreklotz.quickdesklite.controller.admin;

import fr.alexandreklotz.quickdesklite.error.ContractException;
import fr.alexandreklotz.quickdesklite.error.ContractorException;
import fr.alexandreklotz.quickdesklite.model.Contractor;
import fr.alexandreklotz.quickdesklite.service.ContractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class ContractorController {

    private ContractorService contractorService;

    @Autowired
    ContractorController(ContractorService contractorService){
        this.contractorService = contractorService;
    }

    //////////////////
    // REST METHODS //
    //////////////////

    @GetMapping("/admin/contractor/all")
    public List<Contractor> getAllContractors(){
        return contractorService.getAllContractors();
    }

    @GetMapping("/admin/contractor/{contractorId}")
    public Contractor getContractorById(@PathVariable UUID contractorId) throws ContractorException {
        return contractorService.getContractorById(contractorId);
    }

    @GetMapping("/admin/contractor/{contractorName}")
    public Contractor getContractorByName(@PathVariable String contractorName) throws ContractorException{
        return contractorService.getContractorByName(contractorName);
    }

    @PostMapping("/admin/contractor/new")
    public Contractor createContractor(@RequestBody Contractor contractor) throws ContractException {
        return contractorService.createContractor(contractor);
    }

    @PostMapping("/admin/contractor/update")
    public Contractor updateContractor(@RequestBody Contractor contractor) throws ContractorException, ContractException {
        return contractorService.updateContractor(contractor);
    }

    @DeleteMapping("/admin/contractor/delete")
    public void deleteContractor(@RequestBody Contractor contractor) {
        contractorService.deleteContractor(contractor);
    }

}
