package fr.alexandreklotz.quickdesk.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    //@JsonView
    private int deviceId;

    @Column(nullable = false)
    private String deviceName;

    @Column
    private String deviceManufacturer;

    @Column
    private String deviceSerialNbr;

    @Column
    private String deviceDesc;

    /////////////
    //Relations//
    /////////////

    //A device can be used by only one user at a time but a user can use multiple devices
    @ManyToOne
    private User user;

    //A device can be assigned to multiple tickets and one ticket can be assigned to multiple devices
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "deviceTickets",
            joinColumns = { @JoinColumn(name = "deviceId")},
            inverseJoinColumns = {@JoinColumn(name = "ticketId")}
    )
    Set<Ticket> ticketsDev = new HashSet<>();

    //A device can be linked to only one contract but a contract can have multiple devices linked to it
    @JsonView(CustomJsonView.DeviceView.class)
    @ManyToOne
    private Contract contract;

}
