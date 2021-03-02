package com.microservicios.app.respuestas.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.microservicios.commons.alumnas.models.entity.Alumna;
import com.microservicios.commons.examenes.models.entity.Pregunta;

@Entity
@Table(name = "respuestas")
public class Respuesta {

	//Atributos -----------------------------------------------------
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String texto;
	
	//@ManyToOne(fetch = FetchType.LAZY) //una alumna puede tener muchas respuestas
	@Transient //Al ser @Transient no está mapeado a la base de datos
	private Alumna alumna;
	
	@Column(name = "alumna_id") //a través de este campo se establece la relación con las alumnas
	private Long alumnaId; //creamos un campo para guardar el id de la alumna 
	
	@OneToOne(fetch = FetchType.LAZY) //una respuesta es de una pregunta
	private Pregunta pregunta;

	
	//Getters y Setters ---------------------------------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Alumna getAlumna() {
		return alumna;
	}

	public void setAlumna(Alumna alumna) {
		this.alumna = alumna;
	}

	public Pregunta getPregunta() {
		return pregunta;
	}

	public void setPregunta(Pregunta pregunta) {
		this.pregunta = pregunta;
	}

	public Long getAlumnaId() {
		return alumnaId;
	}

	public void setAlumnaId(Long alumnaId) {
		this.alumnaId = alumnaId;
	}
	
}
