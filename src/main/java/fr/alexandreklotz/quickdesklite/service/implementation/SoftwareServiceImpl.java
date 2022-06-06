package fr.alexandreklotz.quickdesklite.service.implementation;

import fr.alexandreklotz.quickdesklite.model.LicenseKey;
import fr.alexandreklotz.quickdesklite.model.Software;
import fr.alexandreklotz.quickdesklite.repository.ContractRepository;
import fr.alexandreklotz.quickdesklite.repository.LicenseKeyRepository;
import fr.alexandreklotz.quickdesklite.repository.SoftwareRepository;
import fr.alexandreklotz.quickdesklite.service.SoftwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SoftwareServiceImpl implements SoftwareService {

    private SoftwareRepository softwareRepository;
    private ContractRepository contractRepository;
    private LicenseKeyRepository licenseKeyRepository;

    @Autowired
    SoftwareServiceImpl(SoftwareRepository softwareRepository, ContractRepository contractRepository, LicenseKeyRepository licenseKeyRepository){
        this.softwareRepository = softwareRepository;
        this.contractRepository = contractRepository;
        this.licenseKeyRepository = licenseKeyRepository;
    }

    @Override
    public List<Software> getAllSoftware() {
        return softwareRepository.findAll();
    }

    @Override
    public Software getSpecifiedSoftware(UUID softwareId) {
        Optional<Software> searchedSoftware = softwareRepository.findById(softwareId);
        if(searchedSoftware.isPresent()){
            return searchedSoftware.get();
        } else {
            return null; //return an error
        }
    }

    @Override
    public Software createSoftware(Software software) {
        if(software.getLicenses() != null){
            for(LicenseKey license : software.getLicenses()){
                Optional<LicenseKey> lkey = licenseKeyRepository.findById(license.getId());
                if(lkey.isPresent()){
                    lkey.get().setSoftware(software);
                    licenseKeyRepository.saveAndFlush(lkey.get());
                }
            }
        }
        softwareRepository.saveAndFlush(software);
        return software;
    }

    @Override
    public Software updateSoftware(Software software) {
        Optional<Software> updSoft = softwareRepository.findById(software.getId());
        if(updSoft.isPresent()){
            if(software.getLicenses() != null){
                for(LicenseKey license : software.getLicenses()){
                    Optional<LicenseKey> lkey = licenseKeyRepository.findById(license.getId());
                    if(lkey.isPresent()){
                        lkey.get().setSoftware(software);
                        licenseKeyRepository.saveAndFlush(lkey.get());
                    }
                }
            }
            softwareRepository.saveAndFlush(updSoft.get());
            return updSoft.get();
        } else {
            return null; //return an error
        }
    }

    @Override
    public void deleteSoftware(Software software) {
        softwareRepository.delete(software);
    }
}
