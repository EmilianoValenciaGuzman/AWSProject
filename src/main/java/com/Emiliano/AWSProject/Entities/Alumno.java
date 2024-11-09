package com.Emiliano.AWSProject.Entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

public class Alumno {
    @NotNull(message = "El ID no puede ser nulo")
    private Integer id;

    @NotBlank(message = "El nombre no puede estar en blanco")
    private String nombres;

    @NotBlank(message = "El apellido no puede estar en blanco")
    private String apellidos;

    @NotNull(message = "La matrícula no puede ser nula")
    @Pattern(regexp = "^[A-Za-z].*", message = "La matrícula debe comenzar con una letra")
    private String matricula;

    @NotNull(message = "El promedio no puede ser nulo")
    @Positive(message = "El promedio debe ser un número positivo")
    private Integer promedio;


    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public Integer getPromedio() { return promedio; }
    public void setPromedio(Integer promedio) { this.promedio = promedio; }
}
