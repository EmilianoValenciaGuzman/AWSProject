package com.Emiliano.AWSProject.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.Emiliano.AWSProject.Entities.Alumno;
import com.Emiliano.AWSProject.Services.AlumnoService;

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




@RestController 
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;
    
    @GetMapping("/") // This annotation tells Spring that this method will be called when a GET request is made to the root URL
    public String getMethodName() {
        return "Hola estoy en PRIMERA ENTREGA";
    }

    @PostMapping("/alumnos")
    public ResponseEntity<Alumno> postAlumno(@Valid @RequestBody Alumno alumno) {
        alumnoService.agregarAlumno(alumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(alumno);
    }

    @GetMapping("/alumnos")
    public ResponseEntity<List<Alumno>> getAlumnos() {
        return ResponseEntity.ok(alumnoService.getAlumnos());
    }
    
    @GetMapping("/alumnos/{id}")
    public ResponseEntity<Alumno> getAlumnoById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(alumnoService.getAlumnoById(Integer.parseInt(id)));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("alumnos/{id}")
    public ResponseEntity<Alumno> updateAlumno(@Valid @RequestBody Alumno alumno, @PathVariable String id){
        try {

            return ResponseEntity.ok(alumnoService.actualizarAlumno(Integer.parseInt(id), alumno));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("alumnos/{id}")
    public ResponseEntity<String> deleteAlumno(@PathVariable String id){
        try {
            alumnoService.eliminarAlumno(Integer.parseInt(id));
            return ResponseEntity.ok("Alumno con " +  id + " eliminado");
        } catch (NumberFormatException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID inválido");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alumno no encontrado");
        }
    }
}
