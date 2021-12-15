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
public class Device {

    //TODO : Implement an history of previous users with the dates at which they used the device. See if modifications have to be made in other classes aswell (in which there is a relation with this model)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @JsonView(CustomJsonView.DeviceView.class)
    private int deviceId;

    @JsonView({CustomJsonView.DeviceView.class, CustomJsonView.UserView.class, CustomJsonView.ContractView.class, CustomJsonView.TicketView.class})
    @Column(nullable = false)
    private String deviceName;

    @JsonView({CustomJsonView.DeviceView.class, CustomJsonView.ContractView.class})
    @Column
    private String deviceManufacturer;

    @JsonView({CustomJsonView.DeviceView.class, CustomJsonView.ContractView.class})
    @Column
    private String deviceSerialNbr;

    @JsonView(CustomJsonView.DeviceView.class)
    @Column
    private String deviceDesc;

    /////////////
    //Relations//
    /////////////

    //A device can be used by only one user at a time but a user can use multiple devices
    @JsonView(CustomJsonView.DeviceView.class)
    @ManyToOne
    private User user;

    //A device can be assigned to multiple tickets and one ticket can be assigned to multiple devices
    @JsonView(CustomJsonView.DeviceView.class)
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "deviceTickets",
            joinColumns = { @JoinColumn(name = "deviceId")},
            inverseJoinColumns = {@JoinColumn(name = "ticketId")}
    )
    private Set<Ticket> ticket;


    //A device can be linked to only one contract but a contract can have multiple devices linked to it
    @JsonView(CustomJsonView.DeviceView.class)
    @ManyToOne
    private Contract contract;

    //To software

}
