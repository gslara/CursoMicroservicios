package com.microservicios.app.usuarios.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservicios.app.usuarios.client.CursoFeignClient;
import com.microservicios.app.usuarios.models.repository.AlumnaRepository;
import com.microservicios.commons.alumnas.models.entity.Alumna;
import com.microservicios.commons.services.CommonServiceImpl;

@Service
public class AlumnaServiceImpl extends CommonServiceImpl<Alumna, AlumnaRepository> implements AlumnaService {

	//Inyección de nuestro cliente feign -------------
	@Autowired 
	private CursoFeignClient cursoClient;
	
	
	//Métodos ----------------------------------------
	@Override
	@Transactional(readOnly = true) //readOnly porque solo es una consulta
	public List<Alumna> buscarPorNombreOApellido(String texto) {
		return repository.buscarPorNombreOApellido(texto);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumna> findAllById(Iterable<Long> ids) {
		return repository.findAllById(ids);
	}

	@Override
	public void eliminarCursoAlumnaPorId(Long id) {
		cursoClient.eliminarCursoAlumnaPorId(id);
	}

	@Override //Sobreescribimos este método para eliminar también la alumna de CursoAlumna
	@Transactional
	public void deleteById(Long id) {
		super.deleteById(id);
		this.eliminarCursoAlumnaPorId(id); //después de borrar la alumna, vamos a borrarla de CursoAlumna
	}

}


