package com.Emiliano.AWSProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Emiliano.AWSProject.Entities.Alumno;

public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {
    // JpaRepository ya tiene métodos como save(), findAll(), findById(), etc.
    
}
