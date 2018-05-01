package com.arana.diego.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
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

	// calculo percio total para carrito vip
	@Override
	public BigDecimal calculateTotalPrice(){
		
		if(!this.listProduct.isEmpty() && this.listProduct != null){
			BigDecimal total = calculateTotal();
			if(this.listProduct.size() == 5){
				BigDecimal discount = total.multiply(new BigDecimal(0.20));
				total = total.subtract(discount);
			}else if (this.listProduct.size() > 10){
				BigDecimal discount = new BigDecimal(700);
				discount = discount.add(lowerCost(this.listProduct));
				total = total.subtract(discount);
			}
			return total;
		}
		return BigDecimal.ZERO;
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
