package com.microservicios.commons.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommonService<E> {

	public Iterable<E> findAll();
	
	public Page<E> findAll(Pageable pageable); //pasamos Pageable como parámetro para que sea paginable. Page es un Iterable por rangos, para paginar
	
	public Optional<E> findById(Long id); //usamos Optional para evitar errores si lo que trae es un null
	
	public E save(E entity);
	
	public void deleteById(Long id);
	
}

