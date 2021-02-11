package com.microservicios.app.cursos.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.app.cursos.models.entity.Curso;
import com.microservicios.app.cursos.services.CursoService;
import com.microservicios.commons.alumnas.models.entity.Alumna;
import com.microservicios.commons.controllers.CommonController;

@RestController
public class CursoController extends CommonController<Curso, CursoService> {

	// Métodos handler del Request ----------------------------
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@RequestBody Curso curso, @PathVariable Long id) {
		Optional<Curso> optional = service.findById(id);

		if (optional.isEmpty()) { // nos fijamos si no existe una alumna con ese id
			return ResponseEntity.notFound().build(); // build construye la respuesta con un body vacío
		}

		Curso cursoDB = optional.get();
		cursoDB.setNombre(curso.getNombre());

		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDB));
	}

	
	@PutMapping("/{id}/asignar-alumnas")
	public ResponseEntity<?> asignarAlumnas(@RequestBody List<Alumna> alumnas, @PathVariable Long id) {
		Optional<Curso> optional = service.findById(id);

		if (optional.isEmpty()) { //nos fijamos si no existe un curso con ese id
			return ResponseEntity.notFound().build(); //build construye la respuesta con un body vacío
		}
		
		Curso cursoDB = optional.get();
		alumnas.forEach(a -> { //hacemos una función flecha para agregar a cada alumna
			cursoDB.addAlumna(a);
		});
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDB));
	}
	
	
	@PutMapping("/{id}/eliminar-alumna")
	public ResponseEntity<?> eliminarAlumna(@RequestBody Alumna alumna, @PathVariable Long id) {
		Optional<Curso> optional = service.findById(id);

		if (optional.isEmpty()) { // nos fijamos si no existe un curso con ese id
			return ResponseEntity.notFound().build(); // build construye la respuesta con un body vacío
		}
		
		Curso cursoDB = optional.get();
		cursoDB.removeAlumna(alumna);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDB));
	}
	
	
	@GetMapping("/buscar-por-alumna/{id}")
	public ResponseEntity<?> buscarCurso(@PathVariable Long id) {
		//Curso curso = service.buscarCursoPorIdAlumna(id); //Así lo hace el del curso
		//return ResponseEntity.ok(curso);
		return ResponseEntity.ok(service.buscarCursoPorIdAlumna(id));
	}
	
}
