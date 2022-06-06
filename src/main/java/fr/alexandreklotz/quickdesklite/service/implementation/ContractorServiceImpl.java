package fr.alexandreklotz.quickdesklite.service.implementation;

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
    public Contractor getSpecifiedContractor(Contractor contractor) {
        Optional<Contractor> searchedContractor = contractorRepository.findById(contractor.getId());
        if(searchedContractor.isPresent()){
            return searchedContractor.get();
        } else {
            return null; //return an error instead
        }
    }

    @Override
    public Contractor createContractor(Contractor contractor) {
        if(contractor.getContracts() != null){
            for(Contract contract : contractor.getContracts()){
                Optional<Contract> ctr = contractRepository.findById(contract.getId());
                if(ctr.isPresent()){
                    ctr.get().setContractor(contractor);
                    contractRepository.saveAndFlush(ctr.get());
                }
            }
        }
        contractor.setContractorDateCreated(LocalDateTime.now());
        contractorRepository.saveAndFlush(contractor);
        return contractor;
    }

    @Override
    public Contractor updateContractor(Contractor contractor) {
        Optional<Contractor> updatedContractor = contractorRepository.findById(contractor.getId());
        if(updatedContractor.isPresent()){
            if(contractor.getContracts() != null){
                for(Contract contract : contractor.getContracts()){
                    Optional<Contract> ctr = contractRepository.findById(contract.getId());
                    if(ctr.isPresent()){
                        ctr.get().setContractor(contractor);
                        contractRepository.saveAndFlush(ctr.get());
                    }
                }
            }
            contractorRepository.saveAndFlush(updatedContractor.get());
            return updatedContractor.get();
        } else {
            return null; //return an error
        }
    }

    @Override
    public void deleteContractor(Contractor contractor) {
        contractorRepository.delete(contractor);
    }

}
