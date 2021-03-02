package com.microservicios.app.cursos.services;

import java.util.List;

import com.microservicios.app.cursos.models.entity.Curso;
import com.microservicios.commons.alumnas.models.entity.Alumna;
import com.microservicios.commons.services.CommonService;

public interface CursoService extends CommonService<Curso> {

	public Curso buscarCursoPorIdAlumna(Long id);
	
	public Iterable<Long> obtenerExamenesIdConRespuestasAlumna(Long alumnaId);
	
	public List<Alumna> obtenerAlumnasPorCurso(List<Long> ids);
	
	public void eliminarCursoAlumnaPorId(Long id);
	
}

