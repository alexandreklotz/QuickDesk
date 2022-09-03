package fr.alexandreklotz.quickdesk.service.implementation;

import fr.alexandreklotz.quickdesk.error.ContractException;
import fr.alexandreklotz.quickdesk.error.ContractorException;
import fr.alexandreklotz.quickdesk.model.Contract;
import fr.alexandreklotz.quickdesk.model.Contractor;
import fr.alexandreklotz.quickdesk.repository.ContractRepository;
import fr.alexandreklotz.quickdesk.repository.ContractorRepository;
import fr.alexandreklotz.quickdesk.service.ContractorService;
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
        return contractorRepository.getContractorByName(name).orElseThrow(()
        -> new ContractorException("ERROR : The contractor " + name + " doesn't exist."));
    }

    @Override
    public Contractor createContractor(Contractor contractor) throws ContractException {
        if(contractor.getContracts() != null){
            for(Contract contract : contractor.getContracts()){
                Optional<Contract> ctr = contractRepository.findById(contract.getId());
                if(ctr.isEmpty()){
                    throw new ContractException("ERROR : The contract " + contract.getId() + " doesn't exist");
                }
            }
        }
        contractor.setContractorDateCreated(LocalDateTime.now());
        contractorRepository.saveAndFlush(contractor);
        return contractor;
    }

    @Override
    public Contractor updateContractor(Contractor contractor) throws ContractorException, ContractException {

        Optional<Contractor> updatedContractor = contractorRepository.findById(contractor.getId());

        if(updatedContractor.isEmpty()){
            throw new ContractorException("ERROR : The contractor " + contractor.getId() + " doesn't exist.");
        }

        if(contractor.getContracts() != null){
            for(Contract contract : contractor.getContracts()){
                Optional<Contract> ctr = contractRepository.findById(contract.getId());
                if(ctr.isEmpty()){
                    throw new ContractException("ERROR : The contract " + contract.getId() + " doesn't exist.");
                }
            }
        }

        contractorRepository.saveAndFlush(contractor);
        return contractor;
    }

    @Override
    public void deleteContractorById(UUID contId) {
        contractorRepository.deleteById(contId);
    }

}
