package com.microservicios.commons.examenes.models.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "asignaturas")
public class Asignatura {

	//Atributos --------------------------------------
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	@JsonIgnoreProperties(value = {"hijas"})
	@ManyToOne(fetch = FetchType.LAZY) //muchas asignaturas hijas, pueden tener una madre
	private Asignatura madre;

	@JsonIgnoreProperties(value = {"madre"}, allowSetters = true) //para limitar las relaciones en cascada que son infinitas
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "madre", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Asignatura> hijas;
	
	
	//Constructor ------------------------------------
	public Asignatura() {
		this.hijas = new ArrayList<>();
	}

	
	//Getters y Setters ------------------------------
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

	public Asignatura getMadre() {
		return madre;
	}

	public void setMadre(Asignatura madre) {
		this.madre = madre;
	}

	public List<Asignatura> getHijas() {
		return hijas;
	}

	public void setHijas(List<Asignatura> hijas) {
		this.hijas = hijas;
	}
	
}
