package fr.alexandreklotz.quickdesk.service.implementation;

import fr.alexandreklotz.quickdesk.error.ContractException;
import fr.alexandreklotz.quickdesk.error.LicenseKeyException;
import fr.alexandreklotz.quickdesk.error.SoftwareException;
import fr.alexandreklotz.quickdesk.model.Contract;
import fr.alexandreklotz.quickdesk.model.LicenseKey;
import fr.alexandreklotz.quickdesk.model.Software;
import fr.alexandreklotz.quickdesk.repository.ContractRepository;
import fr.alexandreklotz.quickdesk.repository.LicenseKeyRepository;
import fr.alexandreklotz.quickdesk.repository.SoftwareRepository;
import fr.alexandreklotz.quickdesk.service.SoftwareService;
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
    public Software getSoftwareById(UUID softwareId) throws SoftwareException {
        return softwareRepository.findById(softwareId).orElseThrow(()
        -> new SoftwareException("The specified software doesn't exist."));
    }

    @Override
    public Software getSoftwareByName(String name) throws SoftwareException {
        return softwareRepository.getSoftwareByName(name).orElseThrow(()
        -> new SoftwareException("ERROR : The software " + name + " doesn't exist."));
    }

    @Override
    public Software createSoftware(Software software) throws LicenseKeyException, ContractException {

        if(software.getLicenses() != null){
            for(LicenseKey license : software.getLicenses()){
                Optional<LicenseKey> lkey = licenseKeyRepository.findById(license.getId());
                if(lkey.isEmpty()){
                    throw new LicenseKeyException("ERROR : The license with the key " + license.getId() + " doesn't exist.");
                }
                lkey.get().setSoftware(software);
            }
        }

        if(software.getContract() != null){
            Optional<Contract> contract = contractRepository.findById(software.getContract().getId());
            if(contract.isEmpty()){
                throw new ContractException("ERROR : The contrat with the id " + software.getContract().getId() + " doesn't exist");
            }
            software.setContract(contract.get());
        }

        softwareRepository.saveAndFlush(software);
        return software;
    }


    @Override
    public Software updateSoftware(Software software) throws SoftwareException, LicenseKeyException, ContractException {

        Optional<Software> updatedSoftware = softwareRepository.findById(software.getId());
        if(updatedSoftware.isEmpty()){
            throw new SoftwareException("ERROR : " + software.getName() + " doesn't exist.");
        }

        if(software.getLicenses() != null){
            for(LicenseKey license : software.getLicenses()){
                Optional<LicenseKey> lkey = licenseKeyRepository.findById(license.getId());
                if(lkey.isEmpty()){
                    throw new LicenseKeyException("ERROR : The license with the key " + license.getId() + " doesn't exist.");
                }
                lkey.get().setSoftware(software);
            }
        }

        if(software.getContract() != null){
            Optional<Contract> contract = contractRepository.findById(software.getContract().getId());
            if(contract.isEmpty()){
                throw new ContractException("ERROR : The contrat with the id " + software.getContract().getId() + " doesn't exist");
            }
            software.setContract(contract.get());
        }


        softwareRepository.saveAndFlush(software);
        return software;
    }


    @Override
    public void deleteSoftwareById(UUID softId) {
        softwareRepository.deleteById(softId);
    }
}
