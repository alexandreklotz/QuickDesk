package fr.alexandreklotz.quickdesklite.service.implementation;

import fr.alexandreklotz.quickdesklite.error.LicenseKeyException;
import fr.alexandreklotz.quickdesklite.error.SoftwareException;
import fr.alexandreklotz.quickdesklite.model.LicenseKey;
import fr.alexandreklotz.quickdesklite.model.Software;
import fr.alexandreklotz.quickdesklite.repository.LicenseKeyRepository;
import fr.alexandreklotz.quickdesklite.repository.SoftwareRepository;
import fr.alexandreklotz.quickdesklite.service.LicenseKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LicenseKeyServiceImpl implements LicenseKeyService {

    private LicenseKeyRepository licenseKeyRepository;
    private SoftwareRepository softwareRepository;

    @Autowired
    LicenseKeyServiceImpl(LicenseKeyRepository licenseKeyRepository, SoftwareRepository softwareRepository){
        this.licenseKeyRepository = licenseKeyRepository;
        this.softwareRepository = softwareRepository;
    }

    @Override
    public List<LicenseKey> getAllLicenses() {
        return licenseKeyRepository.findAll();
    }

    @Override
    public LicenseKey getLicensekeyById(UUID licenseId) throws LicenseKeyException {
        return licenseKeyRepository.findById(licenseId).orElseThrow(()
        -> new LicenseKeyException("The license key you specified doesn't exist."));
    }

    @Override
    public LicenseKey createLicenseKey(LicenseKey licenseKey) throws SoftwareException {

        if(licenseKey.getSoftware() != null){
            Optional<Software> licSoftware = softwareRepository.findById(licenseKey.getSoftware().getId());
            if(!licSoftware.isPresent()){
                throw new SoftwareException("The software you're trying to assign to this license key doesn't exist.");
            }
            licenseKey.setSoftware(licSoftware.get());
        }

        licenseKeyRepository.saveAndFlush(licenseKey);
        return licenseKey;
    }


    @Override
    public LicenseKey updateLicenseKey(LicenseKey licenseKey) throws LicenseKeyException, SoftwareException {
        Optional<LicenseKey> updatedLic = licenseKeyRepository.findById(licenseKey.getId());
        if(!updatedLic.isPresent()){
            throw new LicenseKeyException("The license key you're trying to update doesn't exist.");
        }

        if(licenseKey.getSoftware() != null){
            Optional<Software> licSoftware = softwareRepository.findById(licenseKey.getSoftware().getId());
            if(!licSoftware.isPresent()){
                throw new SoftwareException("The software you're trying to assign to this license key doesn't exist.");
            }
            updatedLic.get().setSoftware(licSoftware.get());
        }
        licenseKeyRepository.saveAndFlush(updatedLic.get());
        return updatedLic.get();
    }

    @Override
    public void deleteLicenseKey(LicenseKey licenseKey) {
        licenseKeyRepository.delete(licenseKey);
    }
}
