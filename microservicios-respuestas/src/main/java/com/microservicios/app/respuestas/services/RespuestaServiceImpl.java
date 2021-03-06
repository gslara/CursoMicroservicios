package com.microservicios.app.respuestas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservicios.app.respuestas.models.entity.Respuesta;
import com.microservicios.app.respuestas.models.repository.RespuestaRepository;

@Service
public class RespuestaServiceImpl implements RespuestaService {

	//Inyección de nuestro repositorio ---------------
	@Autowired
	private RespuestaRepository repository;
	
	
	//Métodos ----------------------------------------
	@Override
	@Transactional
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
		return repository.saveAll(respuestas);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Respuesta> buscarRespuestaPorAlumnaPorExamen(Long alumnaId, Long examenId) {
		return repository.buscarRespuestaPorAlumnaPorExamen(alumnaId, examenId);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Long> buscarExamenesConRespuestasPorAlumna(Long alumnaId) {
		return repository.buscarExamenesConRespuestasPorAlumna(alumnaId);
	}

}
