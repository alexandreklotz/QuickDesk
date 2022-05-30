package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.model.Contractor;

import java.util.List;

public interface ContractorService {

    public List<Contractor> getAllContractors();

    public Contractor getSpecifiedContractor(Contractor contractor);

    public Contractor createContractor(Contractor contractor);

    public Contractor updateContractor(Contractor contractor);

    public void deleteContractor(Long contractorId);
}
