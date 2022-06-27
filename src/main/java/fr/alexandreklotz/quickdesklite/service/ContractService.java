package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.error.ContractException;
import fr.alexandreklotz.quickdesklite.error.ContractorException;
import fr.alexandreklotz.quickdesklite.error.SoftwareException;
import fr.alexandreklotz.quickdesklite.model.Contract;

import java.util.List;

public interface ContractService {

    public List<Contract> getAllContracts();

    public Contract getSpecifiedContract(Contract contract) throws ContractException;

    public Contract createContract(Contract contract) throws ContractorException;

    public Contract updateContract(Contract contract) throws ContractException, ContractorException, SoftwareException;

    public void deleteContract(Contract contract);
}
