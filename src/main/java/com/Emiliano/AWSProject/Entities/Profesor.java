package com.Emiliano.AWSProject.Entities;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Profesor {
    @NotNull(message = "El ID no puede ser nulo")
    private int id;

    @NotNull(message = "El número de empleado no puede ser nulo")
    @Positive(message = "El número de empleado debe ser un número positivo")
    private int numeroEmpleado;

    @NotBlank(message = "El nombre no puede estar en blanco")
    @NotNull(message = "El nombre no puede ser nulo")
    String nombres;

    @NotNull(message = "El apellido no puede estar en blanco")
    String apellidos;

    @NotNull(message = "El número de empleado no puede ser nulo")
    @Positive(message = "El número de empleado debe ser un número positivo")
    int horasClase;
    
}
