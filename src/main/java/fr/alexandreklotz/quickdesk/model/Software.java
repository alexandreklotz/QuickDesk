package fr.alexandreklotz.quickdesk.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Software {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @JsonView(CustomJsonView.SoftwareView.class)
    private int softwareId;

    @Column(nullable = false)
    @JsonView(CustomJsonView.SoftwareView.class)
    private String softwareName;

    @Column(nullable = false)
    @JsonView(CustomJsonView.SoftwareView.class)
    private String softwareManufacturer;

    /////////////
    //Relations//
    /////////////

    //A software has a single license but a license can be affected to multiple software
    @ManyToOne
    private License license;

    //A software can be affected to one contract and vice versa
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contractId", referencedColumnName = "contractId")
    private Contract contract;

    //Devices

    ///////////////
    //Constructor//
    ///////////////

    public Software(){}

    /////////////////////
    //Getters & Setters//
    /////////////////////

    //TODO : relations getters and setters
    public int getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(int softwareId) {
        this.softwareId = softwareId;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public String getSoftwareManufacturer() {
        return softwareManufacturer;
    }

    public void setSoftwareManufacturer(String softwareManufacturer) {
        this.softwareManufacturer = softwareManufacturer;
    }
}
