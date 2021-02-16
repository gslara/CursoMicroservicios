package com.microservicios.app.examenes.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.app.examenes.services.ExamenService;
import com.microservicios.commons.controllers.CommonController;
import com.microservicios.commons.examenes.models.entity.Examen;

@RestController
public class ExamenController extends CommonController<Examen, ExamenService> {

	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@RequestBody Examen examen, @PathVariable Long id) {
		
		Optional<Examen> optional = service.findById(id);
		
		if(!optional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Examen examenBD = optional.get();
		examenBD.setNombre(examen.getNombre());
		
		//Eliminamos preguntas del examen -----------------------
//		List<Pregunta> eliminadas = new ArrayList<>();
//		examenBD.getPreguntas().forEach(pbd -> { //pbd: preguntas base de datos
//			if(!examen.getPreguntas().contains(pbd)) { //con contains() preguntamos si un elemento existe en esa lista
//				eliminadas.add(pbd); //si la pregunta no está en el nuevo JSON que el usuario envía, la agregamos a la lista de preguntas para eliminar
//			}
//		});
//		
//		eliminadas.forEach(p -> {
//			examenBD.removePregunta(p); //eliminamos cada pregunta del Array
//		});
		
		//Unaforma más resumida de hacerlo:
		examenBD.getPreguntas().stream() //con stream() convertimos la lista en un flujo
		.filter(pbd -> !examen.getPreguntas().contains(pbd)) //usamos el método filter() para crear una nueva lista con los elementos filtrados, con la condición que le indicamos
		.forEach(examenBD::removePregunta); //iteramos eso mismo con un forEach()

		
		//Agregamos preguntas al examen -------------------------
		examenBD.setPreguntas(examen.getPreguntas());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(examenBD));
	}
	
	
	@GetMapping("/buscar/{texto}")
	public ResponseEntity<?> buscar(@PathVariable String texto) {
		return ResponseEntity.ok(service.buscarPorNombre(texto));
	}
	
	
	@GetMapping("/asignaturas")
	public ResponseEntity<?> listarAsignaturas() {
		return ResponseEntity.ok(service.findAllAsignaturas());
	}
	
}
