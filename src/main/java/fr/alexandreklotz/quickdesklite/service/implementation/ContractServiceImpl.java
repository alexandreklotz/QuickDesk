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

import java.util.List;
import java.util.Optional;

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
    public Contract getSpecifiedContract(Contract contract) throws ContractException {
        return contractRepository.findById(contract.getId()).orElseThrow(()
        -> new ContractException("The specified contract doesn't exist."));
    }

    @Override
    public Contract createContract(Contract contract) throws ContractorException {
        if(contract.getContractor() != null){
            Optional<Contractor> contractor = contractorRepository.findById(contract.getContractor().getId());
            if(!contractor.isPresent()){
                throw new ContractorException("This contractor doesn't exist.");
            }
            contract.setContractor(contractor.get());
        }
        contractRepository.saveAndFlush(contract);
        return contract;
    }

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

        if(contract.getCtrSoftware() != null){
            for(Software software : contract.getCtrSoftware()){
                Optional<Software> ctrSoft = softwareRepository.findById(software.getId());
                if(!ctrSoft.isPresent()){
                    throw new SoftwareException(software.getName() + " doesn't exist and cannot be assigned to the contract.");
                }
                ctrSoft.get().setContract(updatedContract.get());
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
