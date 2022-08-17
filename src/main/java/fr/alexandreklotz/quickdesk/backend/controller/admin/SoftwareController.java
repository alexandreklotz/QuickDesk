package fr.alexandreklotz.quickdesk.backend.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.backend.error.ContractException;
import fr.alexandreklotz.quickdesk.backend.error.LicenseKeyException;
import fr.alexandreklotz.quickdesk.backend.error.SoftwareException;
import fr.alexandreklotz.quickdesk.backend.model.Software;
import fr.alexandreklotz.quickdesk.backend.service.SoftwareService;
import fr.alexandreklotz.quickdesk.backend.view.CustomJsonView;
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

    @JsonView(CustomJsonView.SoftwareView.class)
    @GetMapping("/admin/software/all")
    public List<Software> getAllSoftware(){
        return softwareService.getAllSoftware();
    }

    @JsonView(CustomJsonView.SoftwareView.class)
    @GetMapping("/admin/software/id/{softwareId}")
    public Software getSoftwareById(@PathVariable UUID softwareId) throws SoftwareException {
        return softwareService.getSoftwareById(softwareId);
    }

    @JsonView(CustomJsonView.SoftwareView.class)
    @GetMapping("/admin/software/name/{softName}")
    public Software getSoftwareByName(@PathVariable String softName) throws SoftwareException {
        return softwareService.getSoftwareByName(softName);
    }

    @JsonView(CustomJsonView.SoftwareView.class)
    @PostMapping("/admin/software/create")
    public Software createSoftware(@RequestBody Software software) throws LicenseKeyException, ContractException {
        return softwareService.createSoftware(software);
    }

    @JsonView(CustomJsonView.SoftwareView.class)
    @PutMapping("/admin/software/update")
    public Software updateSoftware(@RequestBody Software software) throws LicenseKeyException, ContractException, SoftwareException {
        return softwareService.updateSoftware(software);
    }

    @JsonView(CustomJsonView.SoftwareView.class)
    @DeleteMapping("/admin/software/id/{softId}/delete")
    public void deleteSoftwareById(@PathVariable UUID softId) {
        softwareService.deleteSoftwareById(softId);
    }
}
