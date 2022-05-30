package fr.alexandreklotz.quickdesklite.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Contract {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(nullable = false, columnDefinition = "BINARY(16)")
    @JsonView(CustomJsonView.ContractView.class)
    private UUID id;

    @JsonView(CustomJsonView.ContractView.class)
    @Column
    private String ctrName;

    @JsonView(CustomJsonView.ContractView.class)
    @Column
    private String ctrComment;

    @JsonView(CustomJsonView.ContractView.class)
    @Column
    private String ctrNumber;

    // TODO : Add date variables

    ///////////////
    //Constructor//
    ///////////////

    public Contract(){}

    /////////////
    //Relations//
    /////////////

    //A contract can have multiple software and vice versa
    @JsonView(CustomJsonView.ContractView.class)
    @ManyToMany
    @JoinTable(
            name = "contract_software",
            joinColumns = @JoinColumn(name = "contract_id"),
            inverseJoinColumns = @JoinColumn(name = "software_id")
    )
    private Set<Software> ctrsoftware;

    //A contract can only have one contractor but a contractor can have multiple contracts
    @JsonView(CustomJsonView.ContractView.class)
    @ManyToOne
    @JoinColumn(name = "contractor_id")
    private Contractor contractor;

    /////////////////////
    //Getters & setters//
    /////////////////////

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCtrName() {
        return ctrName;
    }

    public void setCtrName(String ctrname) {
        this.ctrName = ctrname;
    }

    public String getCtrComment() {
        return ctrComment;
    }

    public void setCtrComment(String ctrcomment) {
        this.ctrComment = ctrcomment;
    }

    public String getCtrNumber() {
        return ctrNumber;
    }

    public void setCtrNumber(String ctrNumber) {
        this.ctrNumber = ctrNumber;
    }

    public Set<Software> getCtrsoftware() {
        return ctrsoftware;
    }

    public void setCtrsoftware(Set<Software> ctrsoftware) {
        this.ctrsoftware = ctrsoftware;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }
}
