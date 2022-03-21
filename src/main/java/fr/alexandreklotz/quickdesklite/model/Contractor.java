package fr.alexandreklotz.quickdesklite.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Contractor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(CustomJsonView.ContractorView.class)
    @Column(nullable = false)
    private Long id;

    @JsonView(CustomJsonView.ContractorView.class)
    @Column(nullable = false)
    private String contractorname;

    @JsonView(CustomJsonView.ContractorView.class)
    @Column
    private String contractorcomment;

    ///////////////
    //Constructor//
    ///////////////

    public Contractor(){}

    /////////////
    //Relations//
    /////////////

    //A contractor can have multiple contracts but a contract can only be assigned to one contractor
    @JsonView(CustomJsonView.ContractorView.class)
    @OneToMany(mappedBy = "contractor")
    private Set<Contract> contracts;

    /////////////////////
    //Getters & setters//
    /////////////////////

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractorname() {
        return contractorname;
    }

    public void setContractorname(String contractorname) {
        this.contractorname = contractorname;
    }

    public String getContractorcomment() {
        return contractorcomment;
    }

    public void setContractorcomment(String contractorcomment) {
        this.contractorcomment = contractorcomment;
    }

    public Set<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }
}
