package fr.alexandreklotz.quickdesklite.model.defaultvalues;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class TicketPriorityDefaultValue {

    @Id
    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketPriorityDefaultValueView.class)
    private String name;

    ///////////////
    //Constructor//
    ///////////////

    public TicketPriorityDefaultValue(){}

    ///////////////////////
    //Getters and setters//
    ///////////////////////

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
