package com.microservicios.app.usuarios.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.microservicios.app.usuarios.services.AlumnaService;
import com.microservicios.commons.alumnas.models.entity.Alumna;
import com.microservicios.commons.controllers.CommonController;

@RestController //controlador de tipo REST para trabajar con MVC (Angular)
public class AlumnaController extends CommonController<Alumna, AlumnaService> {

	//Métodos handler del Request ----------------------------
	@GetMapping("/alumnas-por-curso")
	public ResponseEntity<?> obtenerAlumnasPorCurso(@RequestParam List<Long> ids) {
		return ResponseEntity.ok(service.findAllById(ids));	
	}
	
	
	@GetMapping("/uploads/img/{id}")
	public ResponseEntity<?> verFoto(@PathVariable Long id) {
		
		Optional<Alumna> optional = service.findById(id);
		
		if(optional.isEmpty() || optional.get().getFoto() == null) { //nos fijamos si no existe una alumna con ese id
			return ResponseEntity.notFound().build(); //build construye la respuesta con un body vacío
		}
		
		Resource imagen = new ByteArrayResource(optional.get().getFoto()); //creamos el recurso, la imagen. Le pasamos los bytes con getFoto() como argumento
		
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Alumna alumna, BindingResult result, @PathVariable Long id) {
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
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

	
	@GetMapping("/buscar/{texto}")
	public ResponseEntity<?> buscar(@PathVariable String texto) {
		return ResponseEntity.ok(service.buscarPorNombreOApellido(texto));
	}


	@PostMapping("/crear-con-foto")
	public ResponseEntity<?> crearConFoto(@Valid Alumna alumna, BindingResult result, 
			@RequestParam MultipartFile archivo) throws IOException {

		if(!archivo.isEmpty()) {
			alumna.setFoto(archivo.getBytes());
		}
		
		return super.crear(alumna, result);
	}
	
	
	@PutMapping("/editar-con-foto/{id}")
	public ResponseEntity<?> editarConFoto(@Valid Alumna alumna, BindingResult result, @PathVariable Long id, 
			@RequestParam MultipartFile archivo) throws IOException {
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Alumna> optional = service.findById(id);
		
		if(optional.isEmpty()) { //nos fijamos si no existe una alumna con ese id
			return ResponseEntity.notFound().build(); //build construye la respuesta con un body vacío
		}
		
		Alumna alumnaDB = optional.get();
		alumnaDB.setNombre(alumna.getNombre());
		alumnaDB.setApellido(alumna.getApellido());
		alumnaDB.setEmail(alumna.getEmail());
		
		if(!archivo.isEmpty()) {
			alumnaDB.setFoto(archivo.getBytes());
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnaDB));
	}
	
}





