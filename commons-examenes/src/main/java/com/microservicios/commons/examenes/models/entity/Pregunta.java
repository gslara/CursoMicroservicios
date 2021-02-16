package com.microservicios.commons.examenes.models.entity;

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
@Table(name = "preguntas")
public class Pregunta {

	//Atributos --------------------------------------
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String texto;

	@JsonIgnoreProperties(value = {"preguntas"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "examen_id") //llave foránea, la clase Pregunta es la dueña de la relación porque el @JoinColumn tiene la llave foránea que establece esta relación
	private Examen examen;
	
	
	//GETTERS Y SETTERS ------------------------------
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

	public Examen getExamen() {
		return examen;
	}

	public void setExamen(Examen examen) {
		this.examen = examen;
	}

	
	//Sobreescribimos método equals() --------------------- 
	@Override
	public boolean equals(Object obj) {
			
		if(this==obj) {
			return true; //si el obj es igual, lo elimina en el método remove() de Examen
		}
		
		if(!(obj instanceof Pregunta)) { //si el obj NO es una instancia de Pregunta retornamos false
			return false;
		}
		
		Pregunta p = (Pregunta) obj; //casteamos el obj de tipo Object a un tipo Pregunta para poder comparar el id
		
		return this.id != null && this.id.equals(p.getId()); //validamos que nuestro id sea distinto de nulo y si es igual al id que recibimos. Si se cumplen las dos condiciones, devuelve true
	}
	
}
