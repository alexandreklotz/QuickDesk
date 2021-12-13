package fr.alexandreklotz.quickdesk.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    @JoinColumn(name = "licenseId", referencedColumnName = "licenseId")
    private License license;

    //A software can be affected to one contract and vice versa
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contractId", referencedColumnName = "contractId")
    private Contract contract;

    //Devices
    //TODO : This function will be developed later. The agent will be the key to this function depending on how it retrieves the names etc.

}
