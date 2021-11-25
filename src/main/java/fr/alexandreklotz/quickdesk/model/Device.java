package fr.alexandreklotz.quickdesk.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    //@JsonView
    private int deviceId;

    @Column
    private String deviceName;

    @Column
    private String deviceManufacturer;

    @Column
    private String deviceSerialNbr;

    @Column
    private String deviceDesc;

    //Relations

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



    //Constructor
    public Device(){}


    //Getters and setters
    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceManufacturer() {
        return deviceManufacturer;
    }

    public void setDeviceManufacturer(String deviceManufacturer) {
        this.deviceManufacturer = deviceManufacturer;
    }

    public String getDeviceSerialNbr() {
        return deviceSerialNbr;
    }

    public void setDeviceSerialNbr(String deviceSerialNbr) {
        this.deviceSerialNbr = deviceSerialNbr;
    }

    public String getDeviceDesc() {
        return deviceDesc;
    }

    public void setDeviceDesc(String deviceDesc) {
        this.deviceDesc = deviceDesc;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User userDevice) {
        this.user = userDevice;
    }

    public Set<Ticket> getTicketsDev() {
        return ticketsDev;
    }

    public void setTicketsDev(Set<Ticket> ticketsDev) {
        this.ticketsDev = ticketsDev;
    }
}
