package com.microservicios.app.usuarios.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.app.usuarios.services.AlumnaService;
import com.microservicios.commons.alumnas.models.entity.Alumna;
import com.microservicios.commons.controllers.CommonController;

@RestController //controlador de tipo REST para trabajar con MVC (Angular)
public class AlumnaController extends CommonController<Alumna, AlumnaService> {

	//Métodos handler del Request ----------------------------
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@RequestBody Alumna alumna, @PathVariable Long id) {
		Optional<Alumna> optional = service.findById(id);
		
		if(optional.isEmpty()) { //nos fijamos si no existe una alumna con ese id
			return ResponseEntity.notFound().build(); //build construye la respuesta con un body vacío
		}
		
		Alumna alumnaDB = optional.get();
		alumnaDB.setNombre(alumna.getNombre());
		alumnaDB.setApellido(alumna.getApellido());
		alumnaDB.setEmail(alumna.getEmail());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnaDB));
	}	
	
}


