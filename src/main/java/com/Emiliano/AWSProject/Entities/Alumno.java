package com.Emiliano.AWSProject.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre no puede estar en blanco")
    @NotNull(message = "El nombre no puede ser nulo")
    private String nombres;

    @NotBlank(message = "El apellido no puede estar en blanco")
    @NotNull(message = "El apellido no puede estar en blanco")
    private String apellidos;

    @NotNull(message = "La matrícula no puede ser nula")
    @Pattern(regexp = "^[A-Za-z][0-9]+$", message = "La matrícula debe comenzar con una letra")
    private String matricula;

    @NotNull(message = "El promedio no puede ser nulo")
    @Positive(message = "El promedio debe ser un número positivo")
    private Double promedio;

    private String fotoPerfilUrl;

    @NotNull(message = "La password no puede ser nula")
    @NotBlank(message = "La password no puede estar en blanco")
    private String password;
}
