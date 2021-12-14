package fr.alexandreklotz.quickdesk.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @JsonView(CustomJsonView.ContractView.class)
    private int contractId;

    @Column(nullable = false)
    @JsonView({CustomJsonView.ContractView.class, CustomJsonView.DeviceView.class, CustomJsonView.ContractorView.class})
    private String contractNumber;

    @Column
    @JsonView({CustomJsonView.ContractView.class, CustomJsonView.ContractorView.class})
    private String contractName;

    /////////////
    //Relations//
    /////////////

    //A contract can only be linked to one contractor but a contractor can be linked to multiple contracts
    @JsonView(CustomJsonView.ContractView.class)
    @ManyToOne
    @JoinColumn(name = "contractorId")
    private Contractor contractor;

    //To device
    @JsonView({CustomJsonView.ContractView.class, CustomJsonView.DeviceView.class})
    @OneToMany(mappedBy = "contract")
    private List<Device> device;

    //To license
    @JsonView({CustomJsonView.ContractView.class, CustomJsonView.LicenseView.class})
    @OneToMany(mappedBy = "contract")
    private List<License> license;

    //To software
    @JsonView({CustomJsonView.ContractView.class, CustomJsonView.SoftwareView.class})
    @OneToMany(mappedBy = "contract")
    private List<Software> software;

}
