package com.Emiliano.AWSProject.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Emiliano.AWSProject.Entities.Alumno;

@Service
public class AlumnoService {

    private final List<Alumno> alumnos;

    public AlumnoService(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    public List<Alumno> getAlumnos() {
        return new ArrayList<>(alumnos);
    }

    public void agregarAlumno(Alumno alumno) {
        alumnos.add(alumno);
    }
}
