package com.microservicios.commons.examenes.models.entity;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "examenes")
public class Examen {

	//Atributos --------------------------------------
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty //NotEmpty es para los String
	@Size(min = 4, max = 30)
	private String nombre;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_at")
	private Date createAt;
	
	@JsonIgnoreProperties(value = {"examen"}, allowSetters = true) //como hay relación en ambos lados, el JSON va a incluir el atributo preguntas (con el GetPreguntas) pero cada pregunta tiene un examen, y GetExamen también va a devolver las preguntas. Así que usamos @JsonIgnoreProperties para establecer un límite en la construcción del JSON y evitar este bucle infinito. Permitimos setters para que no haya ningún error
	@OneToMany(mappedBy = "examen", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true) //el cascade type all es para que cuando se elimine/cree un examen, se eliminen/creen también sus preguntas, ya que estas no existen sin examen
	private List<Pregunta> preguntas; //el orphanRemoval es para que cuando una pregunta no esté asignada a un examen (que la llave foránea sea null) sea eliminada. Con mappedBy indicamos el nombre del atributo que mappea la relación (con el @JoinColumn) para que la relación sea bidireccional

	@ManyToOne(fetch = FetchType.LAZY) //muchos exámenes para una asignatura. Relación unidireccional
	@NotNull //se valida con NotNull porque es un objeto
	private Asignatura asignatura;
	
	
	//Constructor ------------------------------------
	public Examen() {
		this.preguntas = new ArrayList<>();
	}


	//Antes de persistir -----------------------------
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
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

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public List<Pregunta> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas.clear(); //así reiniciamos this.preguntas, ya que el setPreguntas() asigna y MODIFICA el listado de preguntas que teníamos.
		preguntas.forEach(this::addPregunta); //por cada pregunta la asignamos al examen usando el addPreugunta(). Los :: nos dicen que el parámetro que se recibe en this es el mismo que se envía como argumento
		//preguntas.forEach(p -> this.addPregunta(p));
	}
	
	public Asignatura getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}
	
	
	///Métodos add y remove pregunta -----------------
	public void addPregunta(Pregunta pregunta) {
		this.preguntas.add(pregunta); //agregamos cada pregunta al examen
		pregunta.setExamen(this); //por cada pregunta también tenemos que incluir el examen en la pregunta. Así establecemos la relación inversa
	}
	
	public void removePregunta(Pregunta pregunta) {
		this.preguntas.remove(pregunta); //quitamos cada pregunta del examen
		pregunta.setExamen(null); //por cada pregunta también tenemos que quitar el id del examen en la pregunta. De esta forma la pregunta queda huérfana y por el orphanRemoval se elimina.
	}

	
	//Sobreescribimos método equals() --------------------- 
	@Override
	public boolean equals(Object obj) {
			
		if(this==obj) {
			return true; //si el obj es igual, lo elimina en el método remove() de Curso
		}
			
		if(!(obj instanceof Examen)) { //si el obj NO es una instancia de Examen retornamos false
			return false;
		}
			
		Examen e = (Examen) obj; //casteamos el obj de tipo Object a un tipo Examen para poder comparar el id
			
		return this.id != null && this.id.equals(e.getId()); //validamos que nuestro id sea distinto de nulo y si es igual al id que recibimos. Si se cumplen las dos condiciones, devuelve true
	}
	
}
