package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.error.ContractException;
import fr.alexandreklotz.quickdesklite.error.ContractorException;
import fr.alexandreklotz.quickdesklite.model.Contractor;

import java.util.List;

public interface ContractorService {

    public List<Contractor> getAllContractors();

    public Contractor getSpecifiedContractor(Contractor contractor) throws ContractorException;

    public Contractor createContractor(Contractor contractor) throws ContractException;

    public Contractor updateContractor(Contractor contractor) throws ContractorException, ContractException;

    public void deleteContractor(Contractor contractor);
}
