package fr.alexandreklotz.quickdesk.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.dao.ContractorDao;
import fr.alexandreklotz.quickdesk.model.Contractor;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @GetMapping("/contractors/all")
    public ResponseEntity<List<Contractor>> getAllContractors(){
        return ResponseEntity.ok(contractorDao.findAll());
    }

}
