package fr.alexandreklotz.quickdesk.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.dao.ContractorDao;
import fr.alexandreklotz.quickdesk.model.Contractor;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class ContractorController {

    private ContractorDao contractorDao;

    @Autowired
    ContractorController (ContractorDao contractorDao) {
        this.contractorDao = contractorDao;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.ContractorView.class)
    @GetMapping("/contractor/all")
    public ResponseEntity<List<Contractor>> getAllContractors(){
        return ResponseEntity.ok(contractorDao.findAll());
    }

    @JsonView(CustomJsonView.ContractorView.class)
    @PostMapping("/contractor/new")
    public void createNewContractor (@RequestBody Contractor contractor) {
        contractorDao.saveAndFlush(contractor);
    }

    /*@JsonView(CustomJsonView.ContractorView.class)
    @PatchMapping("/contractor/update/{contractorId}")
    public ResponseEntity<?> updateContractor*/

    @JsonView(CustomJsonView.ContractorView.class)
    @DeleteMapping("/contractor/delete/{contractorId}")
    public String deleteContractor(@PathVariable int contractorId) {

        Optional<Contractor> contractorBdd = contractorDao.findById(contractorId);

        if (contractorBdd.isPresent()) {
            String cName = contractorBdd.get().getContractorName();
            contractorDao.deleteById(contractorId);
            return "The contractor " + cName + " has been succesfully deleted.";
        }
        return "This contractor doesn't exist.";
    }


}
