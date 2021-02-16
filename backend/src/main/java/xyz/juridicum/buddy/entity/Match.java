package xyz.juridicum.buddy.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "match")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "buddy1")
    private String buddy1;

    @Column(nullable = false, name = "buddy2")
    private String buddy2;

    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;

    @Column(nullable = false, name="examDate", columnDefinition = "DATE")
    private LocalDate examDate;

    @Column(nullable = false, name = "onCreate", columnDefinition = "DATE")
    private LocalDateTime onCreate;
}
