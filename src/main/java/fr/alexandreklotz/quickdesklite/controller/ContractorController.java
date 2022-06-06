package fr.alexandreklotz.quickdesklite.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.model.Contractor;
import fr.alexandreklotz.quickdesklite.repository.ContractRepository;
import fr.alexandreklotz.quickdesklite.repository.ContractorRepository;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
public class ContractorController {

    private ContractorRepository contractorRepository;
    private ContractRepository contractRepository;

    @Autowired
    ContractorController(ContractRepository contractRepository, ContractorRepository contractorRepository){
        this.contractRepository = contractRepository;
        this.contractorRepository = contractorRepository;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.ContractorView.class)
    @GetMapping("/admin/contractor/all")
    public ResponseEntity<List<Contractor>> getAllContractors(){
        return ResponseEntity.ok(contractorRepository.findAll());
    }

    @JsonView(CustomJsonView.ContractorView.class)
    @GetMapping("/admin/contractor/{contractorid}")
    public ResponseEntity<Contractor> getSpecifiedContractor(@PathVariable UUID contractorid){
        Optional<Contractor> contractorBdd = contractorRepository.findById(contractorid);
        if(contractorBdd.isPresent()){
            return ResponseEntity.ok(contractorBdd.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
