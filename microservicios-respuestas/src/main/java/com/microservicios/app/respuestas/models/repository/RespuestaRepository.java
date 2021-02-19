package com.microservicios.app.respuestas.models.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.microservicios.app.respuestas.models.entity.Respuesta;

public interface RespuestaRepository extends CrudRepository<Respuesta, Long> {

	@Query("select r from Respuesta r join fetch r.alumna a join fetch r.pregunta p join fetch p.examen e where a.id=?1 and e.id=?2")
	public Iterable<Respuesta> buscarRespuestaPorAlumnaPorExamen(Long alumnaId, Long examenId);
	
	@Query("select e.id from Respuesta r join r.alumna a join r.pregunta p join p.examen e where a.id=?1 group by e.id") //con group by agrupamos los id que son iguales
	public Iterable<Long> buscarExamenesConRespuestasPorAlumna(Long alumnaId); //devuelve un iterable con los id de los examenes respondidos por una alumna
	
}
