package com.Emiliano.AWSProject.Entities;

import jakarta.validation.constraints.*;


public class Alumno {
    @NotNull(message = "El ID no puede ser nulo")
    private Integer id;

    @NotBlank(message = "El nombre no puede estar en blanco")
    @NotNull(message = "El nombre no puede ser nulo")
    private String nombres;

    @NotNull(message = "El apellido no puede estar en blanco")
    private String apellidos;

    @NotNull(message = "La matrícula no puede ser nula")
    @Pattern(regexp = "^[A-Za-z][0-9]+$", message = "La matrícula debe comenzar con una letra")
    private String matricula;

    @NotNull(message = "El promedio no puede ser nulo")
    @Positive(message = "El promedio debe ser un número positivo")
    private Double promedio;


    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public Double getPromedio() { return promedio; }
    public void setPromedio(Double promedio) { this.promedio = promedio; }
}
