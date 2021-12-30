package fr.alexandreklotz.quickdesklite.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = true)
    private String commentText;

    @Column(nullable = true)
    private Date commentDate;

    /////////////
    //Relations//
    /////////////

    //A comment can only be linked to one ticket but a ticket can have multiple comments
    @JsonView(CustomJsonView.CommentView.class)
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

}
