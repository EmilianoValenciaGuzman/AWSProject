package com.Emiliano.AWSProject.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.Emiliano.AWSProject.Entities.Profesor;

@Service
public class ProfesorService {

    private final List<Profesor> profesores;

    public ProfesorService(List<Profesor> profesores) {
        this.profesores = profesores;
    }

    public void agregarProfesor(Profesor profesor) {
        profesores.add(profesor);
    }

    public List<Profesor> getProfesores() {
        return new ArrayList<>(profesores);
    }

    public Profesor getProfesorById(int id) {
        for (Profesor profesor : profesores) {
            if (profesor.getId() == id) {
                return profesor;
            }
        }
        throw new IllegalArgumentException("No existe un profesor con el id " + id);
    }

    public Profesor actualizarProfesor(int id, Profesor profesor) {
        for (int i = 0; i < profesores.size(); i++) {
            if (profesores.get(i).getId() == id) {
                profesores.set(i, profesor);
                return profesores.get(i);
            }
        }
        throw new IllegalArgumentException("No se pudo actualizar al profesor con el id " + id);
    }

    public void eliminarProfesor(int id) {
        for (int i = 0; i < profesores.size(); i++) {
            if (profesores.get(i).getId() == id) {
                profesores.remove(i);
                return;
            }
        }
        throw new IllegalArgumentException("No se pudo eliminar al profesor con el id " + id);
    }
}