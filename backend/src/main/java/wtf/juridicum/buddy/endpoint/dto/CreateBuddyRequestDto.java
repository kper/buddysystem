package wtf.juridicum.buddy.endpoint.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class CreateBuddyRequestDto {
    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    private Long courseId;

    @NotNull
    @FutureOrPresent
    private LocalDate examDate;
}
