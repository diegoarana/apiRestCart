package com.arana.diego.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy=false)
public abstract class Cart {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idUser")
	private User user;
	
	@OneToMany(mappedBy="product")
	private List<CartProduct> listProduct;
	
	private BigDecimal totalAmount;
	
	public Cart(){}


	public Cart(Long id, User user, List<CartProduct> listProduct, BigDecimal totalAmount) {
		super();
		this.id = id;
		this.user = user;
		this.listProduct = listProduct;
		this.totalAmount = totalAmount;
	}


	public abstract Double calculateTotalPrice();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public List<CartProduct> getListProduct() {
		return listProduct;
	}


	public void setListProduct(List<CartProduct> listProduct) {
		this.listProduct = listProduct;
	}


	public BigDecimal getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	

}
