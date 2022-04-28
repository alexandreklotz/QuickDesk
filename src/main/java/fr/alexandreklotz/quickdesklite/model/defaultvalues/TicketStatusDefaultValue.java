package fr.alexandreklotz.quickdesklite.model.defaultvalues;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class TicketStatusDefaultValue {

    @Id
    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketStatusDefaultValueView.class)
    private String name;

    ///////////////
    //Constructor//
    ///////////////

    public TicketStatusDefaultValue(){}

    /////////////////////
    //Getters & setters//
    /////////////////////

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}