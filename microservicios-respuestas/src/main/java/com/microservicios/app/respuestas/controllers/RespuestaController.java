package com.microservicios.app.respuestas.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.app.respuestas.models.entity.Respuesta;
import com.microservicios.app.respuestas.services.RespuestaService;

@RestController
public class RespuestaController {

	//Inyección del servicio ----------------------------------
	@Autowired
	private RespuestaService service;
	
	
	// Métodos handler del Request ----------------------------
	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Iterable<Respuesta> respuestas) { //MÉTODO MODIFICADO CUANDO USAMOS BASES DE DATOS DISTINTAS
		respuestas = ((List<Respuesta>) respuestas).stream().map(r -> { 
			r.setAlumnaId(r.getAlumna().getId()); //asignamos el alumnaId a las respuestas
			return r; //devolvemos la respuesta modificada
		}).collect(Collectors.toList()); //convertimos el flujo en un List 	
		
		Iterable<Respuesta> respuestasDB = service.saveAll(respuestas); //respuestasDB: respuestas que ya se crearon, que se persistieron en la base de datos
		
		return ResponseEntity.status(HttpStatus.CREATED).body(respuestasDB);
	}
	
	
	@GetMapping("/alumna/{alumnaId}/examen/{examenId}")
	public ResponseEntity<?> obtenerRespuestaPorAlumnaPorExamen(@PathVariable Long alumnaId, @PathVariable Long examenId) {
		Iterable<Respuesta> respuestas = service.buscarRespuestaPorAlumnaPorExamen(alumnaId, examenId);
		
		return ResponseEntity.ok(respuestas);
	}
	
	
	@GetMapping("/alumna/{alumnaId}/examenes-respondidos")
	public ResponseEntity<?> obtenerExamenesIdConRespuestasAlumna(@PathVariable Long alumnaId) {
		Iterable<Long> examenesId = service.buscarExamenesConRespuestasPorAlumna(alumnaId);
		return ResponseEntity.ok(examenesId);
	}
	
}
