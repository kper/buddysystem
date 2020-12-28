package wtf.juridicum.buddy.endpoint.dto;

import lombok.Getter;
import lombok.Setter;
import wtf.juridicum.buddy.entity.Course;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class QueueDto {
    private CourseDto course;
    private LocalDate examDate;
    private LocalDateTime onCreate;
}
