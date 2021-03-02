package com.microservicios.app.usuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-cursos") //indicamos el microservicio con el que nos vamos a conectar
public interface CursoFeignClient {

	//Implementamos el método que vamos a consumir de cursos, endpoint con el que nos vamos a comunicar
	@DeleteMapping("/eliminar-alumna/{id}") //usamos la misma ruta y los mismos parámetros
	public void eliminarCursoAlumnaPorId(@PathVariable Long id);
	
}
