package fr.alexandreklotz.quickdesk.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.dao.SoftwareDao;
import fr.alexandreklotz.quickdesk.model.Software;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class SoftwareController {

    private SoftwareDao softwareDao;

    @Autowired
    SoftwareController (SoftwareDao softwareDao){
        this.softwareDao = softwareDao;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.SoftwareView.class)
    @GetMapping("/software/all")
    public ResponseEntity<List<Software>> getAllSoftware(){
        return ResponseEntity.ok(softwareDao.findAll());
    }

    @JsonView(CustomJsonView.SoftwareView.class)
    @PostMapping("/software/new")
    public void addSoftware (@RequestBody Software software) {
        softwareDao.saveAndFlush(software);
    }

    /*@JsonView(CustomJsonView.SoftwareView.class)
      @PatchMapping("/software/update/{softwareId}*/

    @JsonView(CustomJsonView.SoftwareView.class)
    @DeleteMapping("/software/delete/{softwareId}")
    public String deleteSoftware (@PathVariable int softwareId) {

        Optional<Software> softwareBdd = softwareDao.findById(softwareId);

        if(softwareBdd.isPresent()){
            String sftName = softwareBdd.get().getSoftwareName() + " - " + softwareBdd.get().getSoftwareManufacturer();
            softwareDao.deleteById(softwareId);
            return "The software " + softwareId + " has been deleted." + " " + sftName;
        }
        return "The specified software doesn't exist";
    }
}
