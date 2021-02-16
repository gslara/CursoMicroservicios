package com.microservicios.app.usuarios.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.microservicios.commons.alumnas.models.entity.Alumna;

//@Repository no es necesaria la anotación ya que por defecto es un componente de Spring y se puede inyectar
public interface AlumnaRepository extends PagingAndSortingRepository<Alumna, Long> {

	@Query("select a from Alumna a where a.nombre like %?1% or a.apellido like %?1%") //consulta de Hibernate o JPA Query Language (NO es SQL nativa)
	public List<Alumna> buscarPorNombreOApellido(String texto);
	
}
