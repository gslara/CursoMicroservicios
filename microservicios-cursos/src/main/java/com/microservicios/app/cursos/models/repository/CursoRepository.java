package com.microservicios.app.cursos.models.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.microservicios.app.cursos.models.entity.Curso;

public interface CursoRepository extends PagingAndSortingRepository<Curso, Long> {
	
	//CONSULTA MODIFICADA CUANDO USAMOS BASES DE DATOS DISTINTAS: ahora establecemos la relación a través del atributo cursoAlumnas y el id es en realidad alumnaId
	@Query("select c from Curso c join fetch c.cursoAlumnas a where a.alumnaId=?1") //usamos join para obtener el curso de la alumna y fetch para poblar el curso con una lista de alumnas en una sola consulta. Y necesitamos join fetch para preguntar por el id de la alumna 
	public Curso buscarCursoPorIdAlumna(Long id); //devolvemos el curso al que pertenece la alumna que enviamos
	
	@Modifying //para poder modificar los registros con nuestra query
	@Query("delete from CursoAlumna ca where ca.alumnaId=?1")
	public void eliminarCursoAlumnaPorId(Long id); //para eliminar la alumna (que ya no existe) del curso. Pasamos el id de la alumna
	
}
