package fr.alexandreklotz.quickdesk.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

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
    @JsonView(CustomJsonView.ContractView.class)
    private String contractNumber;

    @Column
    @JsonView(CustomJsonView.ContractView.class)
    private String contractName;

    /////////////
    //Relations//
    /////////////

    //A contract can only be linked to one contractor but a contractor can be linked to multiple contracts
    @JsonView({CustomJsonView.ContractView.class, CustomJsonView.ContractorView.class})
    @ManyToOne
    private Contractor contractor;

    //To device
    @JsonView({CustomJsonView.ContractView.class, CustomJsonView.ContractorView.class})
    @OneToMany(mappedBy = "contract")
    private Set<Device> devices;

    //To license
    @JsonView({CustomJsonView.ContractView.class, CustomJsonView.ContractorView.class})
    @OneToMany(mappedBy = "contract")
    private Set<License> licenses;

    //To software
    @OneToOne(mappedBy = "contract")
    private Software software;

}
