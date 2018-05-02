package com.arana.diego.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private int dni;
	private Boolean vip;
	
	@OneToMany(fetch =FetchType.EAGER, mappedBy="user")
	@JsonIgnore
	private List<Cart> lisCart; 
	
	public User(){}

	
	public User(long id, int dni, Boolean vip, List<Cart> lisCart) {
		super();
		this.id = id;
		this.dni = dni;
		this.vip = vip;
		this.lisCart = lisCart;
	}


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getDni() {
		return dni;
	}
	public void setDni(int dni) {
		this.dni = dni;
	}
	public Boolean getVip() {
		return vip;
	}
	public void setVip(Boolean vip) {
		this.vip = vip;
	}
	
	
	public List<Cart> getLisCart() {
		return lisCart;
	}



	public void setLisCart(List<Cart> lisCart) {
		this.lisCart = lisCart;
	}


}
