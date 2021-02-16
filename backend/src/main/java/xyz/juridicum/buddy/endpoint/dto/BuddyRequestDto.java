package xyz.juridicum.buddy.endpoint.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class BuddyRequestDto {
    private Long id;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotBlank
    @PastOrPresent
    private LocalDateTime onCreate;

    @NotNull
    private CourseDto course;

    @NotNull
    @FutureOrPresent
    private LocalDate examDate;

    @NotNull
    private boolean confirmed;
}
