package com.microservicios.app.cursos.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.microservicios.commons.alumnas.models.entity.Alumna;

@FeignClient(name = "microservicio-usuarios") //indicamos el microservicio con el que nos vamos a conectar
public interface AlumnaFeignClient {

	//Implementamos el método que vamos a consumir de usuarios, endpoint con el que nos vamos a comunicar
	@GetMapping("/alumnas-por-curso") //usamos la misma ruta y los mismos parámetros
	public List<Alumna> obtenerAlumnasPorCurso(@RequestParam List<Long> ids);
	
}
