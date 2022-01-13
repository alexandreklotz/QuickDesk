package fr.alexandreklotz.quickdesklite.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Device {

    @JsonView(CustomJsonView.DeviceView.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @JsonView({CustomJsonView.DeviceView.class, CustomJsonView.UtilisateurView.class})
    @Column(nullable = false)
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
    @Column(nullable = false)
    private LocalDateTime deviceCreated;

    ///////////////
    //Constructor//
    ///////////////

    public Device(){}

    /////////////
    //Relations//
    /////////////

    //A device can be assigned to multiple users but a user can only be assigned to one device
    @JsonView(CustomJsonView.DeviceView.class)
    @OneToMany(mappedBy = "device")
    private Set<Utilisateur> deviceUtilisateurs;

    //A device can be assigned to multiple users but a tech/admin can only be assigned to one device
    @JsonView(CustomJsonView.DeviceView.class)
    @OneToMany(mappedBy = "device")
    private Set<Admn> deviceAdmnUtilisateurs;

    /////////////////////
    //Getters & setters//
    /////////////////////


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Set<Utilisateur> getDeviceUtilisateurs() {
        return deviceUtilisateurs;
    }

    public void setDeviceUtilisateurs(Set<Utilisateur> utilisateurs) {
        this.deviceUtilisateurs = utilisateurs;
    }

    public Set<Admn> getDeviceAdmnUtilisateurs() {
        return deviceAdmnUtilisateurs;
    }

    public void setDeviceAdmnUtilisateurs(Set<Admn> deviceAdmnUtilisateurs) {
        this.deviceAdmnUtilisateurs = deviceAdmnUtilisateurs;
    }
}
