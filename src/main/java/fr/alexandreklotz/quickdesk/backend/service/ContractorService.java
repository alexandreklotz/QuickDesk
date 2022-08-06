package fr.alexandreklotz.quickdesk.backend.service;

import fr.alexandreklotz.quickdesk.backend.error.ContractException;
import fr.alexandreklotz.quickdesk.backend.error.ContractorException;
import fr.alexandreklotz.quickdesk.backend.model.Contractor;

import java.util.List;
import java.util.UUID;

public interface ContractorService {

    public List<Contractor> getAllContractors();

    public Contractor getContractorById(UUID contractorId) throws ContractorException;

    public Contractor getContractorByName(String name) throws ContractorException;

    public Contractor createContractor(Contractor contractor) throws ContractException;

    public Contractor updateContractor(Contractor contractor) throws ContractorException, ContractException;

    public void deleteContractorById(UUID contId);
}
