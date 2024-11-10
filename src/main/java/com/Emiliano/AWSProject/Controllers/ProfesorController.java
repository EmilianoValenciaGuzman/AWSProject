package com.Emiliano.AWSProject.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.Emiliano.AWSProject.Entities.Profesor;
import com.Emiliano.AWSProject.Services.ProfesorService;

import java.util.List;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ProfesorController {

    @Autowired
    private ProfesorService profesorService;

    @PostMapping("/profesores")
    public ResponseEntity<Profesor> postProfesor(@Valid @RequestBody Profesor profesor) {
        profesorService.agregarProfesor(profesor);
        return ResponseEntity.status(HttpStatus.CREATED).body(profesor);
    }

    @GetMapping("/profesores")
    public ResponseEntity<List<Profesor>> getProfesores() {
        return ResponseEntity.ok(profesorService.getProfesores());
    }
    
    @GetMapping("/profesores/{id}")
    public ResponseEntity<Profesor> getProfesorById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(profesorService.getProfesorById(Integer.parseInt(id)));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
    @PutMapping("profesores/{id}")
    public ResponseEntity<Profesor> updateProfesor(@Valid @RequestBody Profesor profesor, @PathVariable String id){
        try {

            return ResponseEntity.ok(profesorService.actualizarProfesor(Integer.parseInt(id), profesor));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("profesores/{id}")
    public ResponseEntity<String> deleteProfesor(@PathVariable String id){
        try {
            profesorService.eliminarProfesor(Integer.parseInt(id));
            return ResponseEntity.ok("Profesor con " +id + " eliminado");
        } catch (NumberFormatException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID inválido");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profesor no encontrado");
        }
    }
}
