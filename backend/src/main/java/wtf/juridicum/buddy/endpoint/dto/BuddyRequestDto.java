package wtf.juridicum.buddy.endpoint.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class BuddyRequestDto {
    private Long id;

    @NotNull
    @NotBlank
    @Email
    private String email;

    private LocalDateTime onCreate;
}
