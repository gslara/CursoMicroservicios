package com.microservicios.commons.services;

import java.util.Optional;

public interface CommonService<E> {

	public Iterable<E> findAll();
	
	public Optional<E> findById(Long id); //usamos Optional para evitar errores si lo que trae es un null
	
	public E save(E entity);
	
	public void deleteById(Long id);
	
}

