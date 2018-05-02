package com.arana.diego.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Proxy(lazy=false)
public class Product {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private BigDecimal price;
	
	@OneToMany(mappedBy="product")
	@JsonIgnore
	private List<CartProduct> listCart = new ArrayList<CartProduct>();
	
	public Product(){}
	

	public Product(Long id, String name, BigDecimal price, List<CartProduct> listCart) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.listCart = listCart;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public List<CartProduct> getListCart() {
		return listCart;
	}

	public void setListCart(List<CartProduct> listCart) {
		this.listCart = listCart;
	}
	
    public int hashCode(){
        System.out.println("In hashcode");
        int hashcode = 0;
        hashcode = id.intValue()*20;
        hashcode += name.hashCode();
        return hashcode;
    }
    
    
    public boolean equals(Object obj){
        System.out.println("In equals");
        if (obj instanceof Product) {
            Product pp = (Product) obj;
            return (pp.name.equals(this.name) && pp.id == this.id);
        } else {
            return false;
        }
    }


}
