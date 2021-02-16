package com.microservicios.app.examenes.services;

import java.util.List;

import com.microservicios.commons.examenes.models.entity.Asignatura;
import com.microservicios.commons.examenes.models.entity.Examen;
import com.microservicios.commons.services.CommonService;

public interface ExamenService extends CommonService<Examen> {

	public List<Examen> buscarPorNombre(String texto);
	
	public List<Asignatura> findAllAsignaturas();
	
}
