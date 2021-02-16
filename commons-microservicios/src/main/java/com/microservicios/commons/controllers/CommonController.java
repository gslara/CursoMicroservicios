package com.microservicios.commons.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
	public ResponseEntity<?> crear(@Valid @RequestBody E entity, BindingResult result) { //con @Valid habilitamos la validación
												//BindingResult va siempre justo después de la clase entity para que funcione, con este obtendremos los mensajes de error
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		E entityDB = service.save(entity); 
		
		return ResponseEntity.status(HttpStatus.CREATED).body(entityDB);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		service.deleteById(id);
		
		return ResponseEntity.noContent().build(); //construimos la respuesta sin cuerpo
	}
	
	
	protected ResponseEntity<?> validar(BindingResult result) { //protected para que podamos utilizarlo en los otros controladores
		Map<String, Object> errores = new HashMap<>(); //con el Map implementamos el JSON con los mensajes de error
		result.getFieldErrors().forEach(err -> {
			errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
		});
		
		return ResponseEntity.badRequest().body(errores);
	}
	
}
