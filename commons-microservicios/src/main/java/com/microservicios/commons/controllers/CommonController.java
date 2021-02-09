package com.microservicios.commons.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.microservicios.commons.services.CommonService;

public class CommonController<E, S extends CommonService<E>> { //tenemos dos valores genéricos, la entidad y el servicio, que hereda del servicio genérico

	//Inyección del repositorio ------------------------------
	@Autowired
	protected S service;
	
	
	//Métodos handler del Request ----------------------------
	@GetMapping
	public ResponseEntity<?> listar() {
		return ResponseEntity.ok().body(service.findAll());
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> verDetalle(@PathVariable Long id) {
		Optional<E> optional = service.findById(id);
		
		if(optional.isEmpty()) { //nos fijamos si no existe una entidad con ese id
			return ResponseEntity.notFound().build(); //build construye la respuesta con un body vacío
		}
		
		return ResponseEntity.ok().body(optional.get());
	}
	
	
	@PostMapping
	public ResponseEntity<?> crear(@RequestBody E entity) {
		E entityDB = service.save(entity);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(entityDB);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		service.deleteById(id);
		
		return ResponseEntity.noContent().build(); //construimos la respuesta sin cuerpo
	}
	
}
