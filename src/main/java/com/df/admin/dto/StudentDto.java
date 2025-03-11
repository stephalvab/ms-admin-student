package com.df.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDto {
    @NotNull(message = "El ID no puede ser nulo.")
    private Long id;
    @NotBlank(message = "El nombre no puede estar vacío.")
    private String name;
    @NotBlank(message = "El apellido no puede estar vacío.")
    private String lastName;
    @Min(value = 1, message = "La edad debe ser mayor a 0.")
    private int age;
    private String status;
}
