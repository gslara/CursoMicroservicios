package com.microservicios.app.usuarios.models.repository;

import org.springframework.data.repository.CrudRepository;

import com.microservicios.commons.alumnas.models.entity.Alumna;

//@Repository no es necesaria la anotación ya que por defecto es un componente de Spring y se puede inyectar
public interface AlumnaRepository extends CrudRepository<Alumna, Long> {

}
