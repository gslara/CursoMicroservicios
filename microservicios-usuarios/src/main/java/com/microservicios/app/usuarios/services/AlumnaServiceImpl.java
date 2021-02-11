package com.microservicios.app.usuarios.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservicios.app.usuarios.models.repository.AlumnaRepository;
import com.microservicios.commons.alumnas.models.entity.Alumna;
import com.microservicios.commons.services.CommonServiceImpl;

@Service
public class AlumnaServiceImpl extends CommonServiceImpl<Alumna, AlumnaRepository> implements AlumnaService {

	@Override
	@Transactional(readOnly = true) //readOnly porque solo es una consulta
	public List<Alumna> buscarPorNombreOApellido(String texto) {
		return repository.buscarPorNombreOApellido(texto);
	}

}


