package com.arana.diego.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Proxy;


@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="discriminator",
    discriminatorType=DiscriminatorType.STRING
)
@DiscriminatorValue(value="c")
@Proxy(lazy=false)
public class Cart {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idUser")
	private User user;
	
	@Cascade(CascadeType.DELETE)
	@OneToMany(fetch=FetchType.EAGER, mappedBy="product", targetEntity= CartProduct.class)
	protected List<CartProduct> listProduct;
	
	private BigDecimal totalAmount;
	
	public Cart(){}


	public Cart(Long id, User user, List<CartProduct> listProduct, BigDecimal totalAmount) {
		super();
		this.id = id;
		this.user = user;
		this.listProduct = listProduct;
		this.totalAmount = totalAmount;
	}
	
	
	// calculo el total de la lista de productos
	public BigDecimal calculateTotal(){
		BigDecimal totalCost = BigDecimal.ZERO;
		for (CartProduct product : this.listProduct){
			totalCost = totalCost.add(calculateItemCost(product.getQuantity(), product.getProduct().getPrice()));
		}
		return totalCost;
	}
	
	//calculo el subtotal de cada item de la lista de productos
	public BigDecimal calculateItemCost(int quantity, BigDecimal price){
	    BigDecimal itemCost  = BigDecimal.ZERO;
	    BigDecimal subtotalCost = BigDecimal.ZERO;
	    itemCost = price.multiply(new BigDecimal(quantity));
	    subtotalCost = subtotalCost.add(itemCost);
		return subtotalCost;
	}

	// calculo precio total de carrito comun
	public BigDecimal calculateTotalPrice(){
		
		if(!this.listProduct.isEmpty() && this.listProduct != null){
			BigDecimal total = calculateTotal();
			if(this.listProduct.size() == 5){
				BigDecimal discount = total.multiply(new BigDecimal(0.20));
				total = total.subtract(discount);
			}else if (this.listProduct.size() > 10){
				BigDecimal discount = new BigDecimal(200);
				total = total.subtract(discount);
			}
			return total;
		}
		return BigDecimal.ZERO;
	}
	
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
