package wtf.juridicum.buddy.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class Queue {
    private Course course;
    private LocalDate examDate;
    private LocalDateTime onCreate;
}
