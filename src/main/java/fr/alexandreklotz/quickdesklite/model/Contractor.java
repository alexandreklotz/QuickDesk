package fr.alexandreklotz.quickdesklite.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Contractor {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(nullable = false, columnDefinition = "BINARY(16)")
    @JsonView(CustomJsonView.ContractorView.class)
    private UUID id;

    @JsonView(CustomJsonView.ContractorView.class)
    @Column(nullable = false)
    private String contractorname;

    @JsonView(CustomJsonView.ContractorView.class)
    @Column
    private String contractorcomment;

    @JsonView(CustomJsonView.ContractorView.class)
    @Column(nullable = false)
    private LocalDateTime contractorDateCreated;

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public LocalDateTime getContractorDateCreated() {
        return contractorDateCreated;
    }

    public void setContractorDateCreated(LocalDateTime contractorDateCreated) {
        this.contractorDateCreated = contractorDateCreated;
    }

    public Set<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }
}
