package fr.alexandreklotz.quickdesk.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class LicenseKey {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(nullable = false, columnDefinition = "BINARY(16)")
    @JsonView({CustomJsonView.LicenseKeyView.class, CustomJsonView.SoftwareView.class})
    private UUID id;

    @JsonView({CustomJsonView.LicenseKeyView.class, CustomJsonView.SoftwareView.class})
    @Column
    private String licenseSerial;

    ///////////////
    //Constructor//
    ///////////////

    public LicenseKey(){}

    /////////////
    //Relations//
    /////////////

    //A licensekey can only be assigned to one software but a software can have multiple license keys.
    @JsonView(CustomJsonView.LicenseKeyView.class)
    @ManyToOne
    @JoinColumn(name = "software_id")
    private Software software;

    /////////////////////
    //Getters & Setters//
    /////////////////////

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLicenseSerial() {
        return licenseSerial;
    }

    public void setLicenseSerial(String licenseSerial) {
        this.licenseSerial = licenseSerial;
    }

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }

    @Override
    public String toString(){
        return "License id : " + this.id + "\n" + "License key : " + this.licenseSerial;
    }
}
