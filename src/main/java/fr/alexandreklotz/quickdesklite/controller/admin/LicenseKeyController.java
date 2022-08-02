package fr.alexandreklotz.quickdesklite.controller.admin;

import fr.alexandreklotz.quickdesklite.error.LicenseKeyException;
import fr.alexandreklotz.quickdesklite.error.SoftwareException;
import fr.alexandreklotz.quickdesklite.model.LicenseKey;
import fr.alexandreklotz.quickdesklite.service.LicenseKeyService;
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

    @GetMapping("/admin/licensekey/{licenseId}")
    public LicenseKey getLicensekeyById(@PathVariable UUID licenseId) throws LicenseKeyException {
        return licenseKeyService.getLicensekeyById(licenseId);
    }

    @PostMapping("/admin/licensekey/new")
    public LicenseKey createLicensekey(@RequestBody LicenseKey licenseKey) throws SoftwareException {
        return licenseKeyService.createLicenseKey(licenseKey);
    }

    @PutMapping("/admin/licensekey/update")
    public LicenseKey updateLicensekey(@RequestBody LicenseKey licenseKey) throws LicenseKeyException, SoftwareException {
        return licenseKeyService.updateLicenseKey(licenseKey);
    }

    @DeleteMapping("/admin/licensekey/delete")
    public void deleteLicensekey(@RequestBody LicenseKey licenseKey){
        licenseKeyService.deleteLicenseKey(licenseKey);
    }
}
