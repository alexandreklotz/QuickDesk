package fr.alexandreklotz.quickdesk.service;

import fr.alexandreklotz.quickdesk.error.ContractException;
import fr.alexandreklotz.quickdesk.error.ContractorException;
import fr.alexandreklotz.quickdesk.error.SoftwareException;
import fr.alexandreklotz.quickdesk.model.Contract;

import java.util.List;
import java.util.UUID;

public interface ContractService {

    public List<Contract> getAllContracts();

    public Contract getContractById(UUID contractId) throws ContractException;

    public Contract getContractByContractNumber(String ctrNbr) throws ContractException;

    public Contract getContractByContractName(String name) throws ContractException;

    public Contract createContract(Contract contract) throws ContractorException, SoftwareException;

    public Contract updateContract(Contract contract) throws ContractException, ContractorException, SoftwareException;

    public void deleteContractById(UUID ctrId);
}
