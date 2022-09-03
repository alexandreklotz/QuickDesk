package fr.alexandreklotz.quickdesk.service.implementation;

import fr.alexandreklotz.quickdesk.error.ContractException;
import fr.alexandreklotz.quickdesk.error.ContractorException;
import fr.alexandreklotz.quickdesk.error.SoftwareException;
import fr.alexandreklotz.quickdesk.model.Contract;
import fr.alexandreklotz.quickdesk.model.Contractor;
import fr.alexandreklotz.quickdesk.model.Software;
import fr.alexandreklotz.quickdesk.repository.ContractRepository;
import fr.alexandreklotz.quickdesk.repository.ContractorRepository;
import fr.alexandreklotz.quickdesk.repository.SoftwareRepository;
import fr.alexandreklotz.quickdesk.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ContractServiceImpl implements ContractService {

    private ContractRepository contractRepository;
    private ContractorRepository contractorRepository;
    private SoftwareRepository softwareRepository;

    @Autowired
    ContractServiceImpl(ContractRepository contractRepository, ContractorRepository contractorRepository, SoftwareRepository softwareRepository){
        this.contractRepository = contractRepository;
        this.contractorRepository = contractorRepository;
        this.softwareRepository = softwareRepository;
    }

    @Override
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    @Override
    public Contract getContractById(UUID contractId) throws ContractException {
        return contractRepository.findById(contractId).orElseThrow(()
        -> new ContractException("ERROR : The specified contract with the id : " + contractId + " doesn't exist."));
    }

    // TODO : Needs testing
    @Override
    public Contract getContractByContractNumber(String ctrNbr) throws ContractException {
        return contractRepository.getContractByContractNumber(ctrNbr).orElseThrow(()
        -> new ContractException("ERROR : The contract with the number " + ctrNbr + " doesn't exist."));
    }

    @Override
    public Contract getContractByContractName(String name) throws ContractException {
        return contractRepository.getContractByContractName(name).orElseThrow(()
        -> new ContractException("ERROR : The contract " + name + " doesn't exist."));
    }

    @Override
    public Contract createContract(Contract contract) throws ContractorException, SoftwareException {
        if (contract.getContractor() != null) {
            Optional<Contractor> contractor = contractorRepository.findById(contract.getContractor().getId());
            if (contractor.isEmpty()) {
                throw new ContractorException("ERROR : The contractor " + contract.getContractor().getId() + " doesn't exist.");
            }
            contract.setContractor(contractor.get());
        }

        Set<Software> ctrSoftwares = new HashSet<>();

        if (contract.getCtrSoftware() != null) {
            for (Software software : contract.getCtrSoftware()) {
                Optional<Software> ctrSoft = softwareRepository.findById(software.getId());
                if (ctrSoftwares.isEmpty()) {
                    throw new SoftwareException("ERROR : " + software.getName() + " doesn't exist and cannot be assigned to a contract.");
                }
                ctrSoftwares.add(ctrSoft.get());
            }
            contract.setCtrSoftware(ctrSoftwares);
        }
        contractRepository.saveAndFlush(contract);
        return contract;
    }

    @Override
    public Contract updateContract(Contract contract) throws ContractException, ContractorException, SoftwareException {
        Optional<Contract> updatedContract = contractRepository.findById(contract.getId());
        if(updatedContract.isEmpty()){
            throw new ContractException("ERROR : The contract " + contract.getId() + " doesn't exist.");
        }

        if(contract.getContractor() != null){
            Optional<Contractor> contractor = contractorRepository.findById(contract.getContractor().getId());
            if(contractor.isEmpty()){
                throw new ContractorException("ERROR : The contractor " + contract.getContractor().getId() + " doesn't exist.");
            }
        }

        Set<Software> ctrSoftware = updatedContract.get().getCtrSoftware();

        if(contract.getCtrSoftware() != null){
            for(Software software : contract.getCtrSoftware()){
                Optional<Software> ctrSoft = softwareRepository.findById(software.getId());
                if(ctrSoft.isEmpty()){
                    throw new SoftwareException("ERROR : " + software.getName() + " doesn't exist and cannot be assigned to a contract.");
                }
                ctrSoftware.add(ctrSoft.get());
            }
        }

        contract.setCtrSoftware(ctrSoftware);

        contractRepository.saveAndFlush(contract);
        return contract;
    }

    @Override
    public void deleteContractById(UUID ctrId) {
        contractRepository.deleteById(ctrId);
    }
}
