package com.microservicios.app.cursos.models.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.microservicios.app.cursos.models.entity.Curso;

public interface CursoRepository extends CrudRepository<Curso, Long> {

	@Query("select c from Curso c join fetch c.alumnas a where a.id=?1") //usamos join para obtener el curso de la alumna y fetch para poblar el curso con una lista de alumnas en una sola consulta. Y necesitamos join fetch para preguntar por el id de la alumna 
	public Curso buscarCursoPorIdAlumna(Long id); //devolvemos el curso al que pertenece la alumna que enviamos
	
}
