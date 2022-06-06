package fr.alexandreklotz.quickdesklite.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.model.Software;
import fr.alexandreklotz.quickdesklite.repository.ContractRepository;
import fr.alexandreklotz.quickdesklite.repository.DeviceRepository;
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
public class SoftwareController {

    private SoftwareRepository softwareRepository;
    private ContractRepository contractRepository;
    private LicenseKeyRepository licenseKeyRepository;
    private DeviceRepository deviceRepository;

    @Autowired
    SoftwareController(SoftwareRepository softwareRepository ,ContractRepository contractRepository, LicenseKeyRepository licenseKeyRepository, DeviceRepository deviceRepository){
        this.softwareRepository = softwareRepository;
        this.contractRepository = contractRepository;
        this.licenseKeyRepository = licenseKeyRepository;
        this.deviceRepository = deviceRepository;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.SoftwareView.class)
    @GetMapping("/admin/software/all")
    public ResponseEntity<List<Software>> getAllSoftware(){
        return ResponseEntity.ok(softwareRepository.findAll());
    }

    @JsonView(CustomJsonView.SoftwareView.class)
    @GetMapping("/admin/software/{softwareid}")
    public ResponseEntity<Software> getSpecifiedSoftware(@PathVariable UUID softwareid){
        Optional<Software> softwareBdd = softwareRepository.findById(softwareid);
        if(softwareBdd.isPresent()){
            return ResponseEntity.ok(softwareBdd.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
