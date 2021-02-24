package com.microservicios.app.cursos.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.app.cursos.models.entity.Curso;
import com.microservicios.app.cursos.services.CursoService;
import com.microservicios.commons.alumnas.models.entity.Alumna;
import com.microservicios.commons.controllers.CommonController;
import com.microservicios.commons.examenes.models.entity.Examen;

@RestController
public class CursoController extends CommonController<Curso, CursoService> {

	//Inyectamos un atributo para testear el balanceador de carga
	@Value("${config.balanceador.test}") //ponemos la configuración del application.properties y @Value va a inyectar en este atributo, el valor de la variable de entorno: BALANCEADOR_TEST. Si no lo encuentra inyecta el texto "por defecto"
	private String balanceadorTest;  
	
	
	//Método para testear el balanceador de carga
	@GetMapping("/balanceador-test")
	public ResponseEntity<?> balanceadorTest() {
		Map<String, Object> respuesta = new HashMap<String, Object>(); //usamos Map para generar un JSON más personalizado
		respuesta.put("balanceador", balanceadorTest);
		respuesta.put("cursos", service.findAll());
		
		return ResponseEntity.ok(respuesta); //va a construir un JSON con 2 atributos: balanceador y cursos.
	}


	// Métodos handler del Request ----------------------------
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
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
		Curso curso = service.buscarCursoPorIdAlumna(id);
		
		//Nos vamos a comunicar con el microservicio respuestas para obtener la lista de exámenes ids respondidos por la alumna
		if(curso != null) { //validamos que la alumna pertenezca a un curso
			List<Long> examenesId = (List<Long>) service.obtenerExamenesIdConRespuestasAlumna(id); //obtenemos la lista de los exámenes respondidos. Tenemos que castear, ya que este método nos devuelve un Iterable
		
			List<Examen> examenes = curso.getExamenes().stream().map(examen -> { //examenes es la nueva variable en la que guardaremos los cambios que realizamos con map. Con el operador map a través de stream() podemos modificar este flujo de forma funcional
				if(examenesId.contains(examen.getId())) { //si el id de un examen está en la lista de exámenes respondidos, seteamos la variable en true
					examen.setRespondido(true);
				}
				return examen; //en el map siempre retornamos el objeto modificado
			}).collect(Collectors.toList()); //con collect() a través de la clase Collectors, convertimos el flujo modificado en una nueva lista de exámenes
		
			curso.setExamenes(examenes); //tenemos que guardar la lista de exámenes modificados en el curso
		}
		
		return ResponseEntity.ok(curso);
	}
	
	
	@PutMapping("/{id}/asignar-examenes")
	public ResponseEntity<?> asignarExamenes(@RequestBody List<Examen> examenes, @PathVariable Long id) {
		Optional<Curso> optional = service.findById(id);

		if (optional.isEmpty()) { //nos fijamos si no existe un curso con ese id
			return ResponseEntity.notFound().build(); //build construye la respuesta con un body vacío
		}
		
		Curso cursoDB = optional.get();
		examenes.forEach(e -> { //hacemos una función flecha para agregar cada examen
			cursoDB.addExamen(e);
		});
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDB));
	}
	
	
	@PutMapping("/{id}/eliminar-examen")
	public ResponseEntity<?> eliminarExamen(@RequestBody Examen examen, @PathVariable Long id) {
		Optional<Curso> optional = service.findById(id);

		if (optional.isEmpty()) { // nos fijamos si no existe un curso con ese id
			return ResponseEntity.notFound().build(); // build construye la respuesta con un body vacío
		}
		
		Curso cursoDB = optional.get();
		cursoDB.removeExamen(examen);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDB));
	}
	
}
