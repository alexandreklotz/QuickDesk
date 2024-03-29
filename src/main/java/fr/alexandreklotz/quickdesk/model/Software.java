package fr.alexandreklotz.quickdesk.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Software {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(nullable = false, columnDefinition = "BINARY(16)")
    @JsonView({CustomJsonView.SoftwareView.class, CustomJsonView.ContractView.class})
    private UUID id;

    @JsonView({CustomJsonView.SoftwareView.class, CustomJsonView.ContractView.class})
    @Column(nullable = false)
    private String name;

    @JsonView(CustomJsonView.SoftwareView.class)
    @Column
    private String editor;

    @JsonView(CustomJsonView.SoftwareView.class)
    @Column
    private String comment;

    ///////////////
    //Constructor//
    ///////////////

    public Software(){}

    /////////////
    //Relations//
    /////////////

    //A software can have multiple license keys but a license key can only be assigned to one software
    @JsonView({CustomJsonView.SoftwareView.class, CustomJsonView.LicenseKeyView.class})
    @OneToMany(mappedBy = "software")
    private Set<LicenseKey> licenses;


    //TODO : Check the relation between software and contract

    //A software can be linked to only one contract but a contract can be linked to multiple software
    @JsonView({CustomJsonView.SoftwareView.class, CustomJsonView.ContractView.class})
    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

    /*@JsonView({CustomJsonView.ContractView.class, CustomJsonView.SoftwareView.class})
    @ManyToMany(mappedBy = "ctrSoftware")
    private Set<Contract> contracts;*/

    /////////////////////
    //Getters & setters//
    /////////////////////


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Set<LicenseKey> getLicenses() {
        return licenses;
    }

    public void setLicenses(Set<LicenseKey> licenses) {
        this.licenses = licenses;
    }

    /*public Set<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }*/

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    @Override
    public String toString(){
        return "Software info" + "\n" + "Software name : " + this.name + "\n" + "Software editor : " + this.editor;
    }
}
