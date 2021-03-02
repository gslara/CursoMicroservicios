package com.microservicios.app.cursos.models.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.microservicios.commons.alumnas.models.entity.Alumna;
import com.microservicios.commons.examenes.models.entity.Examen;

@Entity
@Table(name="cursos")
public class Curso {
	
	//Atributos --------------------------------------
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Size(min = 4, max = 20)
	private String nombre;
	
	@Column(name="create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;

	//@OneToMany(fetch = FetchType.LAZY) //un curso, muchas alumnas. Unidireccional para desacoplar lo más posible
	@Transient
	private List<Alumna> alumnas;
	
	@JsonIgnoreProperties(value = {"curso"}, allowSetters = true)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true) //curso es el atributo de la clase CursoAlumna que mappea la tabla. CascadeType.ALL: si se elimina el curso que se elimine cursoAlumnas
	private List<CursoAlumna> cursoAlumnas;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Examen> examenes;
	
	
	//Antes de persistir -----------------------------
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}
	
	
	//Constructor ------------------------------------
	public Curso() {
		this.alumnas = new ArrayList<>();
		this.examenes = new ArrayList<>();
		this.cursoAlumnas = new ArrayList<>();
	}
	
	
	//GETTERS Y SETTERS ------------------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public List<Alumna> getAlumnas() {
		return alumnas;
	}

	public void setAlumnas(List<Alumna> alumnas) {
		this.alumnas = alumnas;
	}
	
	public List<Examen> getExamenes() {
		return examenes;
	}

	public void setExamenes(List<Examen> examenes) {
		this.examenes = examenes;
	}

	public List<CursoAlumna> getCursoAlumnas() {
		return cursoAlumnas;
	}

	public void setCursoAlumnas(List<CursoAlumna> cursoAlumnas) {
		this.cursoAlumnas = cursoAlumnas;
	}
	
	
	//Add y remove -----------------------------------
	public void addAlumna(Alumna alumna) {
		this.alumnas.add(alumna);
	}
	
	public void removeAlumna(Alumna alumna) {
		this.alumnas.remove(alumna);
	}
	
	public void addExamen(Examen examen) {
		this.examenes.add(examen);
	}
	
	public void removeExamen(Examen examen) {
		this.examenes.remove(examen);
	}
	
	public void addCursoAlumna(CursoAlumna cursoAlumna) {
		this.cursoAlumnas.add(cursoAlumna);
	}

	public void removeCursoAlumna(CursoAlumna cursoAlumna) {
		this.cursoAlumnas.remove(cursoAlumna);
	}
	
}
