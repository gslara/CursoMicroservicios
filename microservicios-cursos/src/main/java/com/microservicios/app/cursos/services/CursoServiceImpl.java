package com.microservicios.app.cursos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservicios.app.cursos.clients.RespuestaFeignClient;
import com.microservicios.app.cursos.models.entity.Curso;
import com.microservicios.app.cursos.models.repository.CursoRepository;
import com.microservicios.commons.services.CommonServiceImpl;

@Service
public class CursoServiceImpl extends CommonServiceImpl<Curso, CursoRepository> implements CursoService {

	//Inyección de nuestro cliente feign -------------
	@Autowired 
	private RespuestaFeignClient client;
	
	
	//Métodos ----------------------------------------
	@Override
	@Transactional(readOnly = true)
	public Curso buscarCursoPorIdAlumna(Long id) {
		return repository.buscarCursoPorIdAlumna(id);
	}

	@Override //el @Transactional no es necesario ya que no es un repositorio, no es una comunicación con base de datos
	public Iterable<Long> obtenerExamenesIdConRespuestasAlumna(Long alumnaId) {
		return client.obtenerExamenesIdConRespuestasAlumna(alumnaId);
	}

}
