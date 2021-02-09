package com.microservicios.app.cursos.models.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.microservicios.commons.alumnas.models.entity.Alumna;

@Entity
@Table(name="cursos")
public class Curso {
	
	//Atributos --------------------------------------
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	@Column(name="create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;

	@OneToMany(fetch = FetchType.LAZY) //un curso, muchas alumnas. Unidireccional para desacoplar lo más posible
	private List<Alumna> alumnas;
	
	
	//Antes de persistir -----------------------------
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}
	
	
	//Constructor ------------------------------------
	public Curso() {
		this.alumnas = new ArrayList<>();
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
	
	public void addAlumna(Alumna alumna) {
		this.alumnas.add(alumna);
	}
	
	public void removeAlumna(Alumna alumna) {
		this.alumnas.remove(alumna);
	}
	
}
