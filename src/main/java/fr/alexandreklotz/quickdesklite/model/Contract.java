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

    //A contract can be linked to multiple software but a software can only be linked to one contract
    @JsonView(CustomJsonView.ContractView.class)
    @OneToMany(mappedBy = "contract")
    private Set<Software> ctrSoftware;

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

    public Set<Software> getCtrSoftware() {
        return ctrSoftware;
    }

    public void setCtrSoftware(Set<Software> softwares) {
        this.ctrSoftware = softwares;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }
}
