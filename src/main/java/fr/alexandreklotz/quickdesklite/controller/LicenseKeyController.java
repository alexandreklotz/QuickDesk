package fr.alexandreklotz.quickdesklite.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.model.LicenseKey;
import fr.alexandreklotz.quickdesklite.repository.LicenseKeyRepository;
import fr.alexandreklotz.quickdesklite.repository.SoftwareRepository;
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
public class LicenseKeyController {

    private SoftwareRepository softwareRepository;
    private LicenseKeyRepository licenseKeyRepository;

    @Autowired
    LicenseKeyController(SoftwareRepository softwareRepository, LicenseKeyRepository licenseKeyRepository){
        this.softwareRepository = softwareRepository;
        this.licenseKeyRepository = licenseKeyRepository;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.LicenseKeyView.class)
    @GetMapping("/admin/licensekey/all")
    public ResponseEntity<List<LicenseKey>> getAllLicenses(){
        return ResponseEntity.ok(licenseKeyRepository.findAll());
    }

    @JsonView(CustomJsonView.LicenseKeyView.class)
    @GetMapping("/admin/licensekey/{licenseid}")
    public ResponseEntity<LicenseKey> getSpecifiedLicense(@PathVariable UUID licenseid){
        Optional<LicenseKey> licenseBdd = licenseKeyRepository.findById(licenseid);
        if(licenseBdd.isPresent()){
            return ResponseEntity.ok(licenseBdd.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
