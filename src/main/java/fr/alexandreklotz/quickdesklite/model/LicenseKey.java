package fr.alexandreklotz.quickdesklite.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class LicenseKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(CustomJsonView.LicenseKeyView.class)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String lkey;

    ///////////////
    //Constructor//
    ///////////////

    public LicenseKey(){}

    /////////////
    //Relations//
    /////////////

    //A licensekey can only be assigned to one software but a software can have multiple license keys.
    @JsonView({CustomJsonView.LicenseKeyView.class, CustomJsonView.SoftwareView.class})
    @ManyToOne
    @JoinColumn(name = "software_id")
    private Software software;

    /////////////////////
    //Getters & Setters//
    /////////////////////

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLkey() {
        return lkey;
    }

    public void setLkey(String lkey) {
        this.lkey = lkey;
    }

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }

    @Override
    public String toString(){
        return "License id : " + this.id + "\n" + "License key : " + this.lkey;
    }
}
