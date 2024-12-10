package com.Emiliano.AWSProject.Controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.Emiliano.AWSProject.Entities.Alumno;
import com.Emiliano.AWSProject.Entities.Session;
import com.Emiliano.AWSProject.Services.AlumnoService;
import com.Emiliano.AWSProject.Services.DynamoDBService;
import com.Emiliano.AWSProject.Services.S3Service;
import com.Emiliano.AWSProject.Services.SnsService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;


@RestController 
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private DynamoDBService dynamoDBService;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private SnsService snsService;

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
    public ResponseEntity<Alumno> getAlumnoById(@PathVariable int id) {
        Alumno alumno = alumnoService.getAlumnoById(id);
        if (alumno == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(alumno);
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
            return ResponseEntity.ok("Alumno con id = " +  id + " eliminado");
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alumno no encontrado");
        }
    }

   @PostMapping("alumnos/{id}/fotoPerfil")
   public ResponseEntity<Map<String, Object>> uploadFotoPerfil(@PathVariable int id, @RequestParam("foto") MultipartFile file) {
      Alumno alumno = alumnoService.getAlumnoById(id);

      try {

         Path tempFile = Files.createTempFile("fotoPerfil", file.getOriginalFilename());
         file.transferTo(tempFile);

         String fotoPerfilUrl = s3Service.uploadFile(tempFile, "fotoPerfilUrl/" + file.getOriginalFilename());
         alumno.setFotoPerfilUrl(fotoPerfilUrl);
         alumnoService.agregarAlumno(alumno);
         

         Map<String, Object> response = new HashMap<>();
         response.put("message", "Foto de perfil subida con éxito");
         response.put("fotoPerfilUrl", fotoPerfilUrl);
         response.put("success", true);

         return ResponseEntity.ok(response);
        } catch (IOException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error al procesar el archivo");
            response.put("success", false);

            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/alumnos/{id}/session/login")
    public ResponseEntity<Map<String, String>> createSession(@RequestBody Map<String, String> requestBody, @PathVariable int id) {
        Alumno alumno = alumnoService.getAlumnoById(id); 
        String password = requestBody.get("password");

        if (!alumno.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Contraseña incorrecta"));
        }

        UUID uuid = UUID.randomUUID();

        String uuidString = uuid.toString();
        long fecha = Instant.now().getEpochSecond();
        int alumnoId = id;
        boolean active = true;
        String sessionString = alumnoService.generateSessionString();
        
        Session session = new Session();
        session.setId(uuidString);
        session.setFecha(fecha);
        session.setAlumnoId(alumnoId);
        session.setActive(active);
        session.setSessionString(sessionString);
        dynamoDBService.saveSession(session);

        Map<String, String> response = new HashMap<>();
        response.put("sessionString", sessionString);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/alumnos/{id}/session/verify")
    public ResponseEntity<Map<String, Boolean>> verifySession(@RequestBody Map<String, String> requestBody, @PathVariable int id) {
        String sessionString = requestBody.get("sessionString");
        boolean result = dynamoDBService.isValidSession(id, sessionString);
        
        return result ? ResponseEntity.status(HttpStatus.OK).body(Map.of("valid", true)) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("valid", false));
    }

    @PostMapping("/alumnos/{id}/session/logout")
    public ResponseEntity<Map<String, Boolean>> logOutSession(@RequestBody Map<String, String> requestBody, @PathVariable int id) {
        String sessionString = requestBody.get("sessionString");
        boolean result = dynamoDBService.logoutSession(id, sessionString);
        
        return result ? ResponseEntity.status(HttpStatus.OK).body(Map.of("success", true)) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false));
    }

    @PostMapping("/alumnos/{id}/email")
    public ResponseEntity<Map<String, String>> sendSns(@PathVariable int id) {
        Alumno alumno = alumnoService.getAlumnoById(id);
        if (alumno == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Alumno no encontrado con el ID: " + id));
        }
        
        String message = "Calificaciones del alumno:\n" +
                     "Nombre: " + alumno.getNombres() + " " + alumno.getApellidos() + "\n" +
                     "Calificaciones: " + alumno.getPromedio();

        String subject = "Información del Alumno: " + alumno.getNombres() + " " + alumno.getApellidos();

        try {
            String messageId = snsService.publishMessage(message, subject);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Mensaje enviado con éxito. ID del mensaje: " + messageId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Error al enviar el mensaje: " + e.getMessage()));
        }
    }
}
