package fr.alexandreklotz.quickdesk.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class License {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @JsonView(CustomJsonView.LicenseView.class)
    private int licenseId;

    @Column(nullable = false)
    @JsonView(CustomJsonView.LicenseView.class)
    private String licenseKey;

    @Column()
    @JsonView(CustomJsonView.LicenseView.class)
    private int licenseMaxDev;

    @Column()
    @JsonView(CustomJsonView.LicenseView.class)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date licenseStartDate;

    @Column()
    @JsonView(CustomJsonView.LicenseView.class)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date licenseEndDate;

    @Column()
    @JsonView(CustomJsonView.LicenseView.class)
    private String licenseDesc;

    /////////////
    //Relations//
    /////////////

    //A license can be affected to only one contract but a contract can have multiple licenses
    //@ManyToOne

    //A software has a single license but a license can be affected to multiple software
    @JsonView(CustomJsonView.LicenseView.class)
    @OneToMany(mappedBy = "license")
    private Set<Software> softwares;

    ///////////////
    //Constructor//
    ///////////////

    public License(){}

    /////////////////////
    //Getters & setters//
    /////////////////////
    //TODO : Add relations getters and setters
    public int getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(int licenseId) {
        this.licenseId = licenseId;
    }

    public String getLicenseKey() {
        return licenseKey;
    }

    public void setLicenseKey(String licenseKey) {
        this.licenseKey = licenseKey;
    }

    public int getLicenseMaxDev() {
        return licenseMaxDev;
    }

    public void setLicenseMaxDev(int licenseMaxDev) {
        this.licenseMaxDev = licenseMaxDev;
    }

    public Date getLicenseStartDate() {
        return licenseStartDate;
    }

    public void setLicenseStartDate(Date licenseStartDate) {
        this.licenseStartDate = licenseStartDate;
    }

    public Date getLicenseEndDate() {
        return licenseEndDate;
    }

    public void setLicenseEndDate(Date licenseEndDate) {
        this.licenseEndDate = licenseEndDate;
    }

    public String getLicenseDesc() {
        return licenseDesc;
    }

    public void setLicenseDesc(String licenseDesc) {
        this.licenseDesc = licenseDesc;
    }
}
