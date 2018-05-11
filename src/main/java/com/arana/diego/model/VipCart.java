package com.arana.diego.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name="cart")
@DiscriminatorValue(value="v")
@Proxy(lazy=false)
public class VipCart extends Cart{
	
	public VipCart() {
		super();
	}

	
//	CALCULO EL DESCUENTO PARA UN CARRITO VIP
	@Override
	 public BigDecimal calculateDiscountByCart(BigDecimal total){
		BigDecimal discount = new BigDecimal(700);
		discount = discount.add(lowerCost(this.listProduct));
		return total.subtract(discount);
	    }
	
	// obtengo el menor precio de la lista de productos
	public BigDecimal lowerCost(List<CartProduct> listProduct){
		List<BigDecimal> priceList = new ArrayList<BigDecimal>();
		for (CartProduct cartProd : listProduct){
			priceList.add(cartProd.getProduct().getPrice());
		}
		return Collections.min(priceList);
	}

}
