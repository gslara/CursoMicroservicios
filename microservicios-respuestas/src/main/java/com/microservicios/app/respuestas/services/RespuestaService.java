package com.microservicios.app.respuestas.services;

import com.microservicios.app.respuestas.models.entity.Respuesta;

public interface RespuestaService {

	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas);

	public Iterable<Respuesta> buscarRespuestaPorAlumnaPorExamen(Long alumnaId, Long examenId);
	
	public Iterable<Long> buscarExamenesConRespuestasPorAlumna(Long alumnaId);
	
}
