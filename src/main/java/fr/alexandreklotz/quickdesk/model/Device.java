package fr.alexandreklotz.quickdesk.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Device {

    @JsonView(CustomJsonView.DeviceView.class)
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @JsonView({CustomJsonView.DeviceView.class, CustomJsonView.UtilisateurView.class})
    @Column
    private String deviceName;

    @JsonView(CustomJsonView.DeviceView.class)
    @Column
    private String deviceDescription;

    @JsonView(CustomJsonView.DeviceView.class)
    @Column
    private String deviceSerialNbr;

    @JsonView(CustomJsonView.DeviceView.class)
    @Column
    private String deviceManufacturer;

    @JsonView(CustomJsonView.DeviceView.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column
    private static LocalDateTime deviceCreated;

    ///////////////
    //Constructor//
    ///////////////

    public Device(){}

    /////////////
    //Relations//
    /////////////

    //A device can be assigned to multiple users but a user can only be assigned to one device
    @JsonView(CustomJsonView.DeviceView.class)
    @OneToOne(mappedBy = "device")
    private Utilisateur utilisateur;

    //A device can only be assigned to one contract but a contract can have multiple devices assigned
    @JsonView(CustomJsonView.DeviceView.class)
    @ManyToOne
    @JoinColumn(name="contract_id")
    private Contract contract;

    /////////////////////
    //Getters & setters//
    /////////////////////


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceDescription() {
        return deviceDescription;
    }

    public void setDeviceDescription(String deviceDescription) {
        this.deviceDescription = deviceDescription;
    }

    public String getDeviceSerialNbr() {
        return deviceSerialNbr;
    }

    public void setDeviceSerialNbr(String deviceSerialNbr) {
        this.deviceSerialNbr = deviceSerialNbr;
    }

    public String getDeviceManufacturer() {
        return deviceManufacturer;
    }

    public void setDeviceManufacturer(String deviceManufacturer) {
        this.deviceManufacturer = deviceManufacturer;
    }

    public LocalDateTime getDeviceCreated() {
        return deviceCreated;
    }

    public void setDeviceCreated(LocalDateTime deviceCreated) {
        this.deviceCreated = deviceCreated;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateurs) {
        this.utilisateur = utilisateurs;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
