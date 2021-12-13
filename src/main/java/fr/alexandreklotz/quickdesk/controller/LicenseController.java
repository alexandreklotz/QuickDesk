package fr.alexandreklotz.quickdesk.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.dao.LicenseDao;
import fr.alexandreklotz.quickdesk.model.License;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class LicenseController {

    private LicenseDao licenseDao;

    @Autowired
    LicenseController (LicenseDao licenseDao){
        this.licenseDao = licenseDao;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.LicenseView.class)
    @GetMapping("/license/all")
    public ResponseEntity<List<License>> getAllLicenses(){
        return ResponseEntity.ok(licenseDao.findAll());
    }

    @JsonView(CustomJsonView.LicenseView.class)
    @PostMapping("/license/new")
    public void newLicense (@RequestBody License license) {
        licenseDao.saveAndFlush(license);
    }

    /*@JsonView(CustomJsonView.LicenseView.class)
    @PatchMapping("/license/update/{licenseId})*/


    @JsonView(CustomJsonView.LicenseView.class)
    @DeleteMapping("/license/delete/{licenseId}")
    public String deleteLicense(@PathVariable int licenseId){

        Optional<License> licenseBdd = licenseDao.findById(licenseId);
        if (licenseBdd.isPresent()){
            String deletedLicense = licenseBdd.get().getLicenseDesc() + " - " + licenseBdd.get().getLicenseKey();
            licenseDao.deleteById(licenseId);
            return "The license " + deletedLicense + " has been succesfully deleted.";
        }
        return "The license with the id " + licenseId + " doesn't exist.";
    }
}
