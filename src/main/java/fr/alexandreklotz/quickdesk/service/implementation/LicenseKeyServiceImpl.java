package fr.alexandreklotz.quickdesk.service.implementation;

import fr.alexandreklotz.quickdesk.error.LicenseKeyException;
import fr.alexandreklotz.quickdesk.error.SoftwareException;
import fr.alexandreklotz.quickdesk.model.LicenseKey;
import fr.alexandreklotz.quickdesk.model.Software;
import fr.alexandreklotz.quickdesk.repository.LicenseKeyRepository;
import fr.alexandreklotz.quickdesk.repository.SoftwareRepository;
import fr.alexandreklotz.quickdesk.service.LicenseKeyService;
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
    public LicenseKey getLicenseKeyByKey(String licKey) throws LicenseKeyException {
        return licenseKeyRepository.getLicenseByLicenseKey(licKey).orElseThrow(()
        -> new LicenseKeyException("ERROR : No license key with the key " + licKey + " exists."));
    }

    @Override
    public LicenseKey createLicenseKey(LicenseKey licenseKey) throws SoftwareException {

        if(licenseKey.getSoftware() != null){
            Optional<Software> licSoftware = softwareRepository.findById(licenseKey.getSoftware().getId());
            if(licSoftware.isEmpty()){
                throw new SoftwareException("ERROR : The software you're trying to assign to this license key doesn't exist.");
            }
            licenseKey.setSoftware(licSoftware.get());
        }

        licenseKeyRepository.saveAndFlush(licenseKey);
        return licenseKey;
    }

    @Override
    public LicenseKey updateLicenseKey(LicenseKey licenseKey) throws LicenseKeyException, SoftwareException {

        Optional<LicenseKey> updatedLic = licenseKeyRepository.findById(licenseKey.getId());
        if(updatedLic.isEmpty()){
            throw new LicenseKeyException("ERROR : The license key you're trying to update doesn't exist.");
        }

        if(licenseKey.getSoftware() != null){
            Optional<Software> licSoftware = softwareRepository.findById(licenseKey.getSoftware().getId());
            if(licSoftware.isEmpty()){
                throw new SoftwareException("ERROR : The software you're trying to assign to this license key doesn't exist.");
            }
            licenseKey.setSoftware(licSoftware.get());
        }

        licenseKeyRepository.saveAndFlush(licenseKey);
        return licenseKey;
    }

    @Override
    public void deleteLicenseKeyById(UUID licId) {
        licenseKeyRepository.deleteById(licId);
    }
}
