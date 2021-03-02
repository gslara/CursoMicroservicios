package com.microservicios.app.usuarios.services;

import java.util.List;

import com.microservicios.commons.alumnas.models.entity.Alumna;
import com.microservicios.commons.services.CommonService;

public interface AlumnaService extends CommonService<Alumna> {
	
	public List<Alumna> buscarPorNombreOApellido(String texto);
	
	public Iterable<Alumna> findAllById(Iterable<Long> ids);
	
	public void eliminarCursoAlumnaPorId(Long id); 
	
}



