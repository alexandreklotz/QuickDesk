package fr.alexandreklotz.quickdesklite.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(CustomJsonView.ContractView.class)
    @Column(nullable = false)
    private Long id;

    @JsonView(CustomJsonView.ContractView.class)
    @Column
    private String ctrname;

    @JsonView(CustomJsonView.ContractView.class)
    @Column
    private String ctrcomment;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCtrname() {
        return ctrname;
    }

    public void setCtrname(String ctrname) {
        this.ctrname = ctrname;
    }

    public String getCtrcomment() {
        return ctrcomment;
    }

    public void setCtrcomment(String ctrcomment) {
        this.ctrcomment = ctrcomment;
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
