package fr.alexandreklotz.quickdesk.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class License {

    //TODO : Embeddable ?

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
    @JsonView(CustomJsonView.LicenseView.class)
    @ManyToOne
    private Contract contract;

    //A software has a single license but a license can be affected to multiple software
    @JsonView(CustomJsonView.LicenseView.class)
    @OneToMany(mappedBy = "license")
    private Set<Software> softwares;


}
