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

	//calculo precio total para carrito con descuento por fecha especial
	@Override
	public BigDecimal calculateTotalPrice(){
		
		if(!this.listProduct.isEmpty() && this.listProduct != null){
			BigDecimal total = calculateTotal();
			int totalProducts = getTotalProducts(this.listProduct);
			if(totalProducts == 5){
				BigDecimal discount = total.multiply(new BigDecimal(0.20));
				BigDecimal discountRounded = discount.setScale(2, RoundingMode.DOWN);
				total = total.subtract(discountRounded);
			}else if (totalProducts > 10){
				BigDecimal discount = new BigDecimal(500);
				total = total.subtract(discount);
			}
			return total;
		}
		return BigDecimal.ZERO;
	}

}
