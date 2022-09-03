package fr.alexandreklotz.quickdesk.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.error.ContractException;
import fr.alexandreklotz.quickdesk.error.ContractorException;
import fr.alexandreklotz.quickdesk.model.Contractor;
import fr.alexandreklotz.quickdesk.service.ContractorService;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
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

    @JsonView(CustomJsonView.ContractorView.class)
    @GetMapping("/admin/contractor/all")
    public List<Contractor> getAllContractors(){
        return contractorService.getAllContractors();
    }

    @JsonView(CustomJsonView.ContractorView.class)
    @GetMapping("/admin/contractor/id/{contractorId}")
    public Contractor getContractorById(@PathVariable UUID contractorId) throws ContractorException {
        return contractorService.getContractorById(contractorId);
    }

    @JsonView(CustomJsonView.ContractorView.class)
    @GetMapping("/admin/contractor/name/{contractorName}")
    public Contractor getContractorByName(@PathVariable String contractorName) throws ContractorException{
        return contractorService.getContractorByName(contractorName);
    }

    @JsonView(CustomJsonView.ContractorView.class)
    @PostMapping("/admin/contractor/create")
    public Contractor createContractor(@RequestBody Contractor contractor) throws ContractException {
        return contractorService.createContractor(contractor);
    }

    @JsonView(CustomJsonView.ContractorView.class)
    @PutMapping("/admin/contractor/update")
    public Contractor updateContractor(@RequestBody Contractor contractor) throws ContractorException, ContractException {
        return contractorService.updateContractor(contractor);
    }

    @JsonView(CustomJsonView.ContractorView.class)
    @DeleteMapping("/admin/contractor/id/{contId}/delete")
    public void deleteContractor(@PathVariable UUID contId) {
        contractorService.deleteContractorById(contId);
    }

}
