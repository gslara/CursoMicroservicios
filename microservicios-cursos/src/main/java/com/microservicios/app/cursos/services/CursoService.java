package com.microservicios.app.cursos.services;

import com.microservicios.app.cursos.models.entity.Curso;
import com.microservicios.commons.services.CommonService;

public interface CursoService extends CommonService<Curso> {

	public Curso buscarCursoPorIdAlumna(Long id);
	
}

