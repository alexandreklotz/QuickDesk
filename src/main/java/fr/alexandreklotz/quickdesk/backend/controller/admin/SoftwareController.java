package fr.alexandreklotz.quickdesk.backend.controller.admin;

import fr.alexandreklotz.quickdesk.backend.error.ContractException;
import fr.alexandreklotz.quickdesk.backend.error.LicenseKeyException;
import fr.alexandreklotz.quickdesk.backend.error.SoftwareException;
import fr.alexandreklotz.quickdesk.backend.model.Software;
import fr.alexandreklotz.quickdesk.backend.service.SoftwareService;
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

    @GetMapping("/admin/software/id/{softwareId}")
    public Software getSoftwareById(@PathVariable UUID softwareId) throws SoftwareException {
        return softwareService.getSoftwareById(softwareId);
    }

    @GetMapping("/admin/software/name/{softName}")
    public Software getSoftwareByName(@PathVariable String softName) throws SoftwareException {
        return softwareService.getSoftwareByName(softName);
    }

    @PostMapping("/admin/software/new")
    public Software createSoftware(@RequestBody Software software) throws LicenseKeyException, ContractException {
        return softwareService.createSoftware(software);
    }

    @PutMapping("/admin/software/update")
    public Software updateSoftware(@RequestBody Software software) throws LicenseKeyException, ContractException, SoftwareException {
        return softwareService.updateSoftware(software);
    }

    @DeleteMapping("/admin/software/id/{softId}/delete")
    public void deleteSoftwareById(@PathVariable UUID softId) {
        softwareService.deleteSoftwareById(softId);
    }
}
