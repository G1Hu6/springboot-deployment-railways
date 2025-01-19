package com.deploy.aws.dto;


import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Objects;

// Simple DTO(Data Transfer Object) for communicate between client and controllers
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class StudentDTO {
    private Long id;

    @NotBlank(message = "Name must be not null and valid")
    @Size(min = 4, max = 10 , message = "Name having range in [4, 10]")
    private String name;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email must be not null and valid")
    private String email;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StudentDTO that = (StudentDTO) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getEmail());
    }
}
