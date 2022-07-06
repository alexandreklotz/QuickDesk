package fr.alexandreklotz.quickdesklite.service.implementation;

import fr.alexandreklotz.quickdesklite.error.ContractException;
import fr.alexandreklotz.quickdesklite.error.ContractorException;
import fr.alexandreklotz.quickdesklite.model.Contract;
import fr.alexandreklotz.quickdesklite.model.Contractor;
import fr.alexandreklotz.quickdesklite.repository.ContractRepository;
import fr.alexandreklotz.quickdesklite.repository.ContractorRepository;
import fr.alexandreklotz.quickdesklite.service.ContractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContractorServiceImpl implements ContractorService {

    private ContractorRepository contractorRepository;
    private ContractRepository contractRepository;

    @Autowired
    ContractorServiceImpl(ContractorRepository contractorRepository, ContractRepository contractRepository){
        this.contractorRepository = contractorRepository;
        this.contractRepository = contractRepository;
    }

    @Override
    public List<Contractor> getAllContractors() {
        return contractorRepository.findAll();
    }

    @Override
    public Contractor getContractorById(UUID contractorId) throws ContractorException {
        return contractorRepository.findById(contractorId).orElseThrow(()
        -> new ContractorException("The specified contractor doesn't exist."));
    }

    @Override
    public Contractor getContractorByName(String name) throws ContractorException {
        Optional<Contractor> searchedContractor = contractorRepository.getContractorByName(name);
        if(!searchedContractor.isPresent()){
            throw new ContractorException(name + " isn't an existing contractor.");
        }
        return searchedContractor.get();
    }

    @Override
    public Contractor createContractor(Contractor contractor) throws ContractException {
        if(contractor.getContracts() != null){
            for(Contract contract : contractor.getContracts()){
                Optional<Contract> ctr = contractRepository.findById(contract.getId());
                if(!ctr.isPresent()){
                    throw new ContractException("This contract doesn't exist");
                }
                ctr.get().setContractor(contractor);
                contractRepository.saveAndFlush(ctr.get());
            }
        }
        contractor.setContractorDateCreated(LocalDateTime.now());
        contractorRepository.saveAndFlush(contractor);
        return contractor;
    }

    @Override
    public Contractor updateContractor(Contractor contractor) throws ContractorException, ContractException {

        Optional<Contractor> updatedContractor = contractorRepository.findById(contractor.getId());

        if(!updatedContractor.isPresent()){
            throw new ContractorException("This contractor doesn't exist.");
        }

        if(contractor.getContracts() != null){
            for(Contract contract : contractor.getContracts()){
                Optional<Contract> ctr = contractRepository.findById(contract.getId());
                if(!ctr.isPresent()){
                    throw new ContractException("This contract doesn't exist.");
                }
                ctr.get().setContractor(contractor);
                contractRepository.saveAndFlush(ctr.get());
            }
        }

        if(contractor.getContractorName() != null){
            updatedContractor.get().setContractorName(contractor.getContractorName());
        }
        if(contractor.getContractorDescription() != null){
            updatedContractor.get().setContractorDescription(contractor.getContractorDescription());
        }

        contractorRepository.saveAndFlush(updatedContractor.get());
        return updatedContractor.get();
    }

    @Override
    public void deleteContractor(Contractor contractor) {
        contractorRepository.delete(contractor);
    }

}
