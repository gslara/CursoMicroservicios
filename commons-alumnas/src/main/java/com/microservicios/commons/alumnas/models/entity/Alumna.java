package com.microservicios.commons.alumnas.models.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="alumnas")
public class Alumna {

	//Atributos --------------------------------------
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	private String apellido;
	private String email;
	
	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;

	
	//Antes de persistir -----------------------------
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}


	//Sobreescribimos método equals() --------------------- 
	@Override
	public boolean equals(Object obj) {
		
		if(this==obj) {
			return true; //si el obj es igual, lo elimina en el método remove() de Curso
		}
		
		if(!(obj instanceof Alumna)) { //si el obj NO es una instancia de Alumna retornamos false
			return false;
		}
		
		Alumna a = (Alumna) obj; //casteamos el obj de tipo Object a un tipo Alumna para poder comparar el id
		
		return this.id != null && this.id.equals(a.getId()); //validamos que nuestro id sea distinto de nulo y si es igual al id que recibimos. Si se cumplen las dos condiciones, devuelve true
	}
	
}
