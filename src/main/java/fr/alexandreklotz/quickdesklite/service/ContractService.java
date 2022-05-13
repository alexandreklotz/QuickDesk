package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.model.Contract;

import java.util.List;

public interface ContractService {

    public List<Contract> getAllContracts();

    public Contract getSpecifiedContract(Contract contract);

    public Contract createContract(Contract contract);

    public Contract updateContract(Contract contract);

    public void deleteContract(Contract contract);
}
