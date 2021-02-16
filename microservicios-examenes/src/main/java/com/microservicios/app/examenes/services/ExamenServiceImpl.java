package com.microservicios.app.examenes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservicios.app.examenes.models.repository.AsignaturaRepository;
import com.microservicios.app.examenes.models.repository.ExamenRepository;
import com.microservicios.commons.examenes.models.entity.Asignatura;
import com.microservicios.commons.examenes.models.entity.Examen;
import com.microservicios.commons.services.CommonServiceImpl;

@Service
public class ExamenServiceImpl extends CommonServiceImpl<Examen, ExamenRepository> implements ExamenService {

	@Autowired //inyectamos la dependencia del repositorio de asignatura
	private AsignaturaRepository asignaturaRepository;
	
	
	//Métodos --------------------------------------------
	@Override
	@Transactional(readOnly = true)
	public List<Examen> buscarPorNombre(String texto) {
		return repository.buscarPorNombre(texto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Asignatura> findAllAsignaturas() {
		return (List<Asignatura>) asignaturaRepository.findAll(); //hacemos un cast para devolver un List, o definir que devolvemos un Iterable en vez de List
	}

}
