package com.Emiliano.AWSProject.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Emiliano.AWSProject.Entities.Profesor;
import com.Emiliano.AWSProject.repositories.ProfesorRepository;

@Service
public class ProfesorService {

    private final List<Profesor> profesores;
    @Autowired
    private ProfesorRepository profesorRepository;

    public ProfesorService(List<Profesor> profesores) {
        this.profesores = profesores;
    }

    public void agregarProfesor(Profesor profesor) {
        profesorRepository.save(profesor);
    }

    public List<Profesor> getProfesores() {
        return profesorRepository.findAll();
    }

    public Profesor getProfesorById(int id) {
        return profesorRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("No existe un profesor con el id " + id));
    }

    public Profesor actualizarProfesor(int id, Profesor profesor) {
        if (!profesorRepository.existsById(id)) {
            throw new IllegalArgumentException("No se pudo actualizar al alumno con el id " + id);
        }

        profesor.setId(id);
        return profesorRepository.save(profesor);
    }

    public void eliminarProfesor(int id) {
        if (!profesorRepository.existsById(id)) {
            throw new IllegalArgumentException("No se pudo eliminar al alumno con el id " + id);
        }

        profesorRepository.deleteById(id);
    }
}