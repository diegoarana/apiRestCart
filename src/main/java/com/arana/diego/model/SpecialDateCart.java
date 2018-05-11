package com.arana.diego.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name="cart")
@DiscriminatorValue(value="s")
@Proxy(lazy=false)
public class SpecialDateCart extends Cart {
	
	public SpecialDateCart() {
		super();
	}

//	CALCULO EL DESCUENTO PARA UN CARRITO CON PROMOCION POR DIA ESPECIAL
	@Override
	 public BigDecimal calculateDiscountByCart(BigDecimal total){
		BigDecimal discount = new BigDecimal(500);
		return total.subtract(discount); 
		
	    }

}
