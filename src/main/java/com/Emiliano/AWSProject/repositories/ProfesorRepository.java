package com.Emiliano.AWSProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Emiliano.AWSProject.Entities.Profesor;

public interface ProfesorRepository extends JpaRepository<Profesor, Integer> {
    // JpaRepository ya tiene métodos como save(), findAll(), findById(), etc.

}
