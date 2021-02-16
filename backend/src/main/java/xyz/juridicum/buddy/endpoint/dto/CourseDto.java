package xyz.juridicum.buddy.endpoint.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CourseDto {
    private Long id;

    @NotNull
    @NotBlank
    @Length(min = 3, max = 1024)
    private String name;
}
