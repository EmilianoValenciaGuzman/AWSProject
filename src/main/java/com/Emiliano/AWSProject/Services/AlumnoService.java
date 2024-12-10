package com.Emiliano.AWSProject.Services;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Emiliano.AWSProject.Entities.Alumno;
import com.Emiliano.AWSProject.repositories.AlumnoRepository;

@Service
public class AlumnoService{

    private final List<Alumno> alumnos;
    @Autowired
    private AlumnoRepository alumnoRepository;

    public AlumnoService(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    public void agregarAlumno(Alumno alumno) {
        alumnoRepository.save(alumno);
    }

    public List<Alumno> getAlumnos() {
        return alumnoRepository.findAll();
    }

    public Alumno getAlumnoById(int id) {
        return alumnoRepository.findById(id).orElse(null);
    }

    public Alumno actualizarAlumno(int id, Alumno alumno) {
        if (!alumnoRepository.existsById(id)) {
            throw new IllegalArgumentException("No se pudo actualizar al alumno con el id " + id);
        }

        alumno.setId(id);
        return alumnoRepository.save(alumno);
    }

    public void eliminarAlumno(int id) {
        if (!alumnoRepository.existsById(id)) {
            throw new NoSuchElementException("No se pudo eliminar al alumno con el id " + id);
        }

        alumnoRepository.deleteById(id);
    }

    public String generateSessionString (){
        int longitud = 128;
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(longitud);

        for (int i = 0; i < longitud; i++) {
            int index = random.nextInt(caracteres.length());
            sb.append(caracteres.charAt(index));
        }

        return sb.toString();
    }
}