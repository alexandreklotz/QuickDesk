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
public class Contractor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @JsonView({CustomJsonView.ContractorView.class, CustomJsonView.ContractView.class})
    private int contractorId;

    @Column(nullable = false)
    @JsonView({CustomJsonView.ContractorView.class, CustomJsonView.ContractView.class})
    private String contractorName;

    //////////////
    //Relations//
    /////////////

    //A contract can only be linked to one contractor but a contractor can be linked to multiple contracts
    @JsonView(CustomJsonView.ContractorView.class)
    @OneToMany(mappedBy = "contractor")
    private List<Contract> contracts;

}
