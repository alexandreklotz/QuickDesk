package fr.alexandreklotz.quickdesklite.controller.admin;

import fr.alexandreklotz.quickdesklite.error.ContractException;
import fr.alexandreklotz.quickdesklite.error.LicenseKeyException;
import fr.alexandreklotz.quickdesklite.error.SoftwareException;
import fr.alexandreklotz.quickdesklite.model.Software;
import fr.alexandreklotz.quickdesklite.service.SoftwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class SoftwareController {

    private SoftwareService softwareService;

    @Autowired
    SoftwareController(SoftwareService softwareService){
        this.softwareService = softwareService;
    }

    ///////////////////
    // ADMIN METHODS //
    //////////////////

    @GetMapping("/admin/software/all")
    public List<Software> getAllSoftware(){
        return softwareService.getAllSoftware();
    }

    @GetMapping("/admin/software/{softwareId}")
    public Software getSoftwareById(@PathVariable UUID softwareId) throws SoftwareException {
        return softwareService.getSoftwareById(softwareId);
    }

    @PostMapping("/admin/software/new")
    public Software createSoftware(@RequestBody Software software) throws LicenseKeyException, ContractException {
        return softwareService.createSoftware(software);
    }

    @PostMapping("/admin/software/update")
    public Software updateSoftware(@RequestBody Software software) throws LicenseKeyException, ContractException, SoftwareException {
        return softwareService.updateSoftware(software);
    }

    @DeleteMapping("/admin/software/delete")
    public void deleteSoftware(@RequestBody Software software) {
        softwareService.deleteSoftware(software);
    }
}
