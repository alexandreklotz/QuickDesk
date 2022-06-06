package fr.alexandreklotz.quickdesklite.service.implementation;

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
    public LicenseKey getSpecifiedLicenseKey(LicenseKey licenseKey) {
        Optional<LicenseKey> searchedLicense = licenseKeyRepository.findById(licenseKey.getId());
        if(searchedLicense.isPresent()){
            return searchedLicense.get();
        } else {
            return null; //return an error
        }
    }

    @Override
    public LicenseKey createLicenseKey(LicenseKey licenseKey) {
        if(licenseKey.getSoftware() != null){
            Optional<Software> licSoftware = softwareRepository.findById(licenseKey.getSoftware().getId());
            if(licSoftware.isPresent()){
                licenseKey.setSoftware(licSoftware.get());
            }
        }
        licenseKeyRepository.saveAndFlush(licenseKey);
        return licenseKey;
    }

    @Override
    public LicenseKey updateLicenseKey(LicenseKey licenseKey) {
        Optional<LicenseKey> updLicense = licenseKeyRepository.findById(licenseKey.getId());
        if(updLicense.isPresent()){
            if(licenseKey.getSoftware() != null){
                Optional<Software> licSoftware = softwareRepository.findById(licenseKey.getSoftware().getId());
                if(licSoftware.isPresent()){
                    licenseKey.setSoftware(licSoftware.get());
                }
            }
            licenseKeyRepository.saveAndFlush(updLicense.get());
            return updLicense.get();
        } else {
            return null; //return an error
        }
    }

    @Override
    public void deleteLicenseKey(LicenseKey licenseKey) {
        licenseKeyRepository.delete(licenseKey);
    }
}
