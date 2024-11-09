package com.Emiliano.AWSProject;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.Emiliano.AWSProject.Entities.Alumno;
import com.Emiliano.AWSProject.Entities.Profesor;

@SpringBootApplication
public class AwsProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsProjectApplication.class, args);
	
	}

	@Bean
    public List<Alumno> listaDeAlumnos() {
        return new ArrayList<>();
    }

	@Bean
	public List<Profesor> listaDeProfesores() {
		return new ArrayList<>();
	}

}
