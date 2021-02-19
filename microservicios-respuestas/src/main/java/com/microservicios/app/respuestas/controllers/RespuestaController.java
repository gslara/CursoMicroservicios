package com.microservicios.app.respuestas.controllers;

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

	@Autowired
	private RespuestaService service;
	
	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Iterable<Respuesta> respuestas) {
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
