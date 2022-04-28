package fr.alexandreklotz.quickdesklite.model.defaultvalues;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class TicketTypeDefaultValue {

    @Id
    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketTypeDefaultValueView.class)
    private String name;

    ///////////////
    //Constructor//
    ///////////////

    public TicketTypeDefaultValue(){}

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
