package fr.alexandreklotz.quickdesk.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @JsonView(CustomJsonView.ContractView.class)
    private int contractId;

    @Column(nullable = false)
    @JsonView(CustomJsonView.ContractView.class)
    private String contractNumber;

    @Column
    @JsonView(CustomJsonView.ContractView.class)
    private String contractName;

    /////////////
    //Relations//
    /////////////

    //A contract can only be linked to one contractor but a contractor can be linked to multiple contracts
    @JsonView({CustomJsonView.ContractView.class, CustomJsonView.ContractorView.class})
    @ManyToOne
    private Contractor contractor;

    //To device

    //To license
    //@OneToMany

    //To software
    @OneToOne(mappedBy = "contract")
    private Software software;

    ///////////////
    //Constructor//
    ///////////////

    public Contract(){}

    //////////////////////
    //Getters & Setters//
    /////////////////////
    //TODO : Add relations getters and setters
    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }
}
