package xyz.juridicum.buddy.endpoint.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class QueueDto {
    private CourseDto course;
    private LocalDate examDate;
    private LocalDateTime onCreate;
}
