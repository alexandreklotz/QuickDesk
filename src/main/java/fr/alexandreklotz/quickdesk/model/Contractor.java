package fr.alexandreklotz.quickdesk.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Contractor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @JsonView({CustomJsonView.ContractorView.class, CustomJsonView.ContractView.class})
    private int contractorId;

    @Column(nullable = false)
    @JsonView({CustomJsonView.ContractorView.class, CustomJsonView.ContractView.class})
    private String contractorName;

    //////////////
    //Relations//
    /////////////

    //A contract can only be linked to one contractor but a contractor can be linked to multiple contracts
    @JsonView({CustomJsonView.ContractorView.class, CustomJsonView.ContractView.class})
    @OneToMany(mappedBy = "contractor")
    private Set<Contract> contracts;


    ///////////////
    //Constructor//
    ///////////////

    public Contractor(){}

    /////////////////////
    //Getters & Setters//
    /////////////////////

    public int getContractorId() {
        return contractorId;
    }

    public void setContractorId(int contractorId) {
        this.contractorId = contractorId;
    }

    public String getContractorName() {
        return contractorName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }

    public Set<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }
}
