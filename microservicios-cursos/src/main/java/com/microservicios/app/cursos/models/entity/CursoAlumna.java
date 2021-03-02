package com.microservicios.app.cursos.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "cursos_alumnas") //tabla intermedia de la relación entre el curso y las alumnas
public class CursoAlumna {

	//Atributos --------------------------------------
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "alumna_id", unique = true)
	private Long alumnaId; //no es una llave foránea, solo sirve para guardar los ids de las alumnas
	
	@JsonIgnoreProperties(value = {"cursoAlumnas"})
	@ManyToOne(fetch = FetchType.LAZY) //relación bidireccional para que se cree una sola tabla
	@JoinColumn(name = "curso_id")
	private Curso curso;

	
	//GETTERS Y SETTERS ------------------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAlumnaId() {
		return alumnaId;
	}

	public void setAlumnaId(Long alumnaId) {
		this.alumnaId = alumnaId;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	
	//Sobreescribimos método equals() --------------------- 
	@Override
	public boolean equals(Object obj) {
			
		if(this==obj) {
			return true; //si el obj es igual, lo elimina en el método remove() de Curso
		}
			
		if(!(obj instanceof CursoAlumna)) { //si el obj NO es una instancia de CursoAlumna retornamos false
			return false;
		}
			
		CursoAlumna ca = (CursoAlumna) obj; //casteamos el obj de tipo Object a un tipo CursoAlumna para poder comparar el id
		
		return this.alumnaId != null && this.alumnaId.equals(ca.getAlumnaId()); //validamos que el id de la alumna sea distinto de nulo y si es igual al id que recibimos. Si se cumplen las dos condiciones, devuelve true
	}
		
}
