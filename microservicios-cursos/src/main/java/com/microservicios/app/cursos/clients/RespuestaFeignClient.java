package com.microservicios.app.cursos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-respuestas") //indicamos el microservicio con el que nos vamos a conectar
public interface RespuestaFeignClient {
	
	//Método sin implementación, para indicar a qué endpoint nos vamos a comunicar, qué parámetros vamos a pasar y el tipo de retorno
	@GetMapping("/alumna/{alumnaId}/examenes-respondidos") //la ruta tiene que ser exactamente la misma con la que nos vamos a comunicar
	public Iterable<Long> obtenerExamenesIdConRespuestasAlumna(@PathVariable Long alumnaId);
	
}
