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

    public void agregarAlumno(Alumno alumno) {
        alumnos.add(alumno);
    }

    public List<Alumno> getAlumnos() {
        return new ArrayList<>(alumnos);
    }

    public Alumno getAlumnoById(int id) {
        for (Alumno alumno : alumnos) {
            if (alumno.getId() == id) {
                return alumno;
            }
        }
        throw new IllegalArgumentException("No existe un alumno con el id " + id);
    }

    public Alumno actualizarAlumno(int id, Alumno alumno) {
        for (int i = 0; i < alumnos.size(); i++) {
            if (alumnos.get(i).getId() == id) {
                alumnos.set(i, alumno);
                return alumnos.get(i);
            }
        }
        throw new IllegalArgumentException("No se pudo actualizar al alumno con el id " + id);
    }

    public void eliminarAlumno(int id) {
        for (int i = 0; i < alumnos.size(); i++) {
            if (alumnos.get(i).getId() == id) {
                alumnos.remove(i);
                return;
            }
        }
        throw new IllegalArgumentException("No se pudo eliminar al alumno con el id " + id);
    }


}
