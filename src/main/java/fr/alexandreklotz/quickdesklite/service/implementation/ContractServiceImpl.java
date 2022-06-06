package fr.alexandreklotz.quickdesklite.service.implementation;

import fr.alexandreklotz.quickdesklite.model.Contract;
import fr.alexandreklotz.quickdesklite.repository.ContractRepository;
import fr.alexandreklotz.quickdesklite.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService {

    private ContractRepository contractRepository;

    @Autowired
    ContractServiceImpl(ContractRepository contractRepository){
        this.contractRepository = contractRepository;
    }

    @Override
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    @Override
    public Contract getSpecifiedContract(Contract contract) {
        Optional<Contract> searchedContract = contractRepository.findById(contract.getId());
        if(searchedContract.isPresent()){
            return searchedContract.get();
        } else {
            return null;
        }
    }

    @Override
    public Contract createContract(Contract contract) {
        contractRepository.saveAndFlush(contract);
        return contract;
    }

    @Override
    public Contract updateContract(Contract contract) {
        Optional<Contract> updatedContract = contractRepository.findById(contract.getId());
        if(updatedContract.isPresent()){
            contractRepository.saveAndFlush(updatedContract.get());
            return updatedContract.get();
        } else {
            return null; //return an error
        }
    }

    @Override
    public void deleteContract(Contract contract) {
        contractRepository.delete(contract);
    }
}
