package fr.alexandreklotz.quickdesklite.service.implementation;

import fr.alexandreklotz.quickdesklite.error.ContractException;
import fr.alexandreklotz.quickdesklite.error.LicenseKeyException;
import fr.alexandreklotz.quickdesklite.error.SoftwareException;
import fr.alexandreklotz.quickdesklite.model.Contract;
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
    public Software getSpecifiedSoftware(UUID softwareId) throws SoftwareException {
        return softwareRepository.findById(softwareId).orElseThrow(()
        -> new SoftwareException("The specified software doesn't exist."));
    }


    @Override
    public Software createSoftware(Software software) throws LicenseKeyException, ContractException {

        if(software.getLicenses() != null){
            for(LicenseKey license : software.getLicenses()){
                Optional<LicenseKey> lkey = licenseKeyRepository.findById(license.getId());
                if(!lkey.isPresent()){
                    throw new LicenseKeyException("The license with the key " + license.getLkey() + " doesn't exist.");
                }
                lkey.get().setSoftware(software);
                licenseKeyRepository.saveAndFlush(lkey.get());
            }
        }

        if(software.getContract() != null){
            Optional<Contract> softCtr = contractRepository.findById(software.getContract().getId());
            if(!softCtr.isPresent()){
                throw new ContractException("The specified contract doesn't exist and therefore cannot be assigned a software.");
            }
            software.setContract(softCtr.get());
        }

        softwareRepository.saveAndFlush(software);
        return software;
    }


    @Override
    public Software updateSoftware(Software software) throws SoftwareException, LicenseKeyException, ContractException {

        Optional<Software> updatedSoftware = softwareRepository.findById(software.getId());
        if(!updatedSoftware.isPresent()){
            throw new SoftwareException(software.getName() + " doesn't exist.");
        }

        if(software.getLicenses() != null){
            for(LicenseKey license : software.getLicenses()){
                Optional<LicenseKey> lkey = licenseKeyRepository.findById(license.getId());
                if(!lkey.isPresent()){
                    throw new LicenseKeyException("The license with the id " + license.getId() + " doesn't exist.");
                }
                lkey.get().setSoftware(updatedSoftware.get());
            }
        }

        if(software.getContract() != null){
            Optional<Contract> softCtr = contractRepository.findById(software.getContract().getId());
            if(!softCtr.isPresent()){
                throw new ContractException("The specified contract doesn't exist and therefore cannot be assigned.");
            }
            updatedSoftware.get().setContract(softCtr.get());
        }

        softwareRepository.saveAndFlush(updatedSoftware.get());
        return updatedSoftware.get();
    }


    @Override
    public void deleteSoftware(Software software) {
        softwareRepository.delete(software);
    }
}
