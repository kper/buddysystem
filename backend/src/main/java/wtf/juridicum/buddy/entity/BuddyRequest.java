package wtf.juridicum.buddy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "buddyrequest")
/**
 * A `BuddyRequest` represents an user which request to get a buddy for exam.
 */
public class BuddyRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;


    @Column(nullable = false, name = "onCreate", columnDefinition = "DATE")
    private LocalDateTime onCreate;
}
