package com.Emiliano.AWSProject.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.Emiliano.AWSProject.Entities.Alumno;
import com.Emiliano.AWSProject.Services.AlumnoService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController // This annotation tells Spring that this class will be a controller
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
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
    @GetMapping("/alumnos/{id}")
    public String getMethodName(@RequestParam String param, @RequestParam String param2) {
        return new String();
    }

    //     @GetMapping
    // public ResponseEntity<List<Alumno>> obtenerAlumnos() {
    //     return ResponseEntity.ok(alumnoService.getAlumnos());
    // }



}
