package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.error.ContractException;
import fr.alexandreklotz.quickdesklite.error.ContractorException;
import fr.alexandreklotz.quickdesklite.error.SoftwareException;
import fr.alexandreklotz.quickdesklite.model.Contract;

import java.util.List;
import java.util.UUID;

public interface ContractService {

    public List<Contract> getAllContracts();

    public Contract getContractById(UUID contractId) throws ContractException;

    public Contract getContractByContractNumber(String ctrNbr) throws ContractException;

    public Contract createContract(Contract contract) throws ContractorException, SoftwareException;

    public Contract updateContract(Contract contract) throws ContractException, ContractorException, SoftwareException;

    public void deleteContract(Contract contract);
}
