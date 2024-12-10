package com.Emiliano.AWSProject.Entities;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Getter
@Setter
@Entity
public class Profesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "El número de empleado no puede ser nulo")
    @Positive(message = "El número de empleado debe ser un número positivo")
    private int numeroEmpleado;

    @NotBlank(message = "El nombre no puede estar en blanco")
    @NotNull(message = "El nombre no puede ser nulo")
    String nombres;

    @NotBlank(message = "El apellido no puede estar en blanco")
    @NotNull(message = "El apellido no puede estar en blanco")
    String apellidos;

    @NotNull(message = "El número de empleado no puede ser nulo")
    @PositiveOrZero(message = "El número de empleado debe ser mayor o igual a 0")
    int horasClase;
    
}
