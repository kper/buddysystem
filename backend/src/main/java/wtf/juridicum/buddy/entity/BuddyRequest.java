package wtf.juridicum.buddy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
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

    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;

    @Column(nullable = false, name="examDate", columnDefinition = "DATE")
    private LocalDate examDate;

    @Column(nullable = false, name = "token")
    private String token;

    @Column(nullable = false, name = "confirmed")
    private boolean confirmed;
}
