package fr.alexandreklotz.quickdesk.backend.controller.admin;

import fr.alexandreklotz.quickdesk.backend.error.LicenseKeyException;
import fr.alexandreklotz.quickdesk.backend.error.SoftwareException;
import fr.alexandreklotz.quickdesk.backend.model.LicenseKey;
import fr.alexandreklotz.quickdesk.backend.service.LicenseKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class LicenseKeyController {

    private LicenseKeyService licenseKeyService;

    @Autowired
    LicenseKeyController(LicenseKeyService licenseKeyService){
        this.licenseKeyService = licenseKeyService;
    }

    ///////////////////
    // ADMIN METHODS //
    ///////////////////

    @GetMapping("/admin/licensekey/all")
    public List<LicenseKey> getAllLicenses(){
        return licenseKeyService.getAllLicenses();
    }

    @GetMapping("/admin/licensekey/id/{licenseId}")
    public LicenseKey getLicensekeyById(@PathVariable UUID licenseId) throws LicenseKeyException {
        return licenseKeyService.getLicensekeyById(licenseId);
    }

    @GetMapping("/admin/licensekey/key/{licKey}")
    public LicenseKey getLicenseKeyByKey(@PathVariable String licKey) throws LicenseKeyException {
        return licenseKeyService.getLicenseKeyByKey(licKey);
    }

    @PostMapping("/admin/licensekey/new")
    public LicenseKey createLicensekey(@RequestBody LicenseKey licenseKey) throws SoftwareException {
        return licenseKeyService.createLicenseKey(licenseKey);
    }

    @PutMapping("/admin/licensekey/update")
    public LicenseKey updateLicensekey(@RequestBody LicenseKey licenseKey) throws LicenseKeyException, SoftwareException {
        return licenseKeyService.updateLicenseKey(licenseKey);
    }

    @DeleteMapping("/admin/licensekey/id/{licId}/delete")
    public void deleteLicensekeyById(@PathVariable UUID licId){
        licenseKeyService.deleteLicenseKeyById(licId);
    }
}
