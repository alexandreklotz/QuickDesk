package fr.alexandreklotz.quickdesklite.model.defaultvalues;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class TicketTypeDefaultValue {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketTypeDefaultValueView.class)
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
