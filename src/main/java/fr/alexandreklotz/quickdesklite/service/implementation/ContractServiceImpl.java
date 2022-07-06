package fr.alexandreklotz.quickdesklite.service.implementation;

import fr.alexandreklotz.quickdesklite.error.ContractException;
import fr.alexandreklotz.quickdesklite.error.ContractorException;
import fr.alexandreklotz.quickdesklite.error.SoftwareException;
import fr.alexandreklotz.quickdesklite.model.Contract;
import fr.alexandreklotz.quickdesklite.model.Contractor;
import fr.alexandreklotz.quickdesklite.model.Software;
import fr.alexandreklotz.quickdesklite.repository.ContractRepository;
import fr.alexandreklotz.quickdesklite.repository.ContractorRepository;
import fr.alexandreklotz.quickdesklite.repository.SoftwareRepository;
import fr.alexandreklotz.quickdesklite.service.ContractService;
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
        -> new ContractException("The specified contract doesn't exist."));
    }

    // TODO : Needs testing
    @Override
    public Contract getContractByContractNumber(String ctrNbr) throws ContractException {
        Optional<Contract> searchedCtr = contractRepository.getContractByContractNumber(ctrNbr);
        if(!searchedCtr.isPresent()){
            throw new ContractException("The contract with the number " + ctrNbr + " doesn't exist.");
        }
        return searchedCtr.get();
    }

    @Override
    public Contract createContract(Contract contract) throws ContractorException, SoftwareException {
        if (contract.getContractor() != null) {
            Optional<Contractor> contractor = contractorRepository.findById(contract.getContractor().getId());
            if (!contractor.isPresent()) {
                throw new ContractorException("This contractor doesn't exist.");
            }
            contract.setContractor(contractor.get());
        }

        Set<Software> ctrSoftwares = new HashSet<>();

        if (contract.getCtrSoftware() != null) {
            for (Software software : contract.getCtrSoftware()) {
                Optional<Software> ctrSoft = softwareRepository.findById(software.getId());
                if (!ctrSoft.isPresent()) {
                    throw new SoftwareException(software.getName() + " doesn't exist and cannot be assigned to a contract.");
                }
                ctrSoftwares.add(ctrSoft.get());
            }
            contract.setCtrSoftware(ctrSoftwares);
        }
        contractRepository.saveAndFlush(contract);
        return contract;
    }

    //TODO : Find a way to make this method less "heavy". Maybe we can just save the new object by replacing the existing one.
    // We'd just need to check that the assigned contractor and softwares exist.
    @Override
    public Contract updateContract(Contract contract) throws ContractException, ContractorException, SoftwareException {
        Optional<Contract> updatedContract = contractRepository.findById(contract.getId());
        if(!updatedContract.isPresent()){
            throw new ContractException("This contract doesn't exist.");
        }

        if(contract.getContractor() != null){
            Optional<Contractor> contractor = contractorRepository.findById(contract.getContractor().getId());
            if(!contractor.isPresent()){
                throw new ContractorException("This contractor doesn't exist.");
            }
            updatedContract.get().setContractor(contractor.get());
        }

        if(contract.getCtrName() != null){
            updatedContract.get().setCtrName(contract.getCtrName());
        }

        if(contract.getCtrComment() != null){
            updatedContract.get().setCtrComment(contract.getCtrComment());
        }

        if(contract.getCtrNumber() != null){
            updatedContract.get().setCtrNumber(contract.getCtrNumber());
        }

        Set<Software> ctrSoftware = updatedContract.get().getCtrSoftware();

        if(contract.getCtrSoftware() != null){
            for(Software software : contract.getCtrSoftware()){
                Optional<Software> ctrSoft = softwareRepository.findById(software.getId());
                if(!ctrSoft.isPresent()){
                    throw new SoftwareException(software.getName() + " doesn't exist and cannot be assigned to a contract.");
                }
                ctrSoftware.add(ctrSoft.get());
            }
        }

        contractRepository.saveAndFlush(updatedContract.get());
        return updatedContract.get();
    }

    @Override
    public void deleteContract(Contract contract) {
        contractRepository.delete(contract);
    }
}
