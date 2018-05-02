
package com.arana.diego.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.arana.diego.data.ICartProductDAO;
import com.arana.diego.model.CartProduct;

public class CartProductService implements ICartProductService{
	
	@Autowired
	ICartProductDAO cartProductDAO;

	public List<CartProduct> getProducts(Long cartId) {
		return cartProductDAO.getProducts(cartId);
	}

	public void addCartProduct(CartProduct cartProduct) {
		cartProductDAO.addCartProduct(cartProduct);
		
	}

	public void updateCartProduct(CartProduct cartProduct) {
		cartProductDAO.updateCartProduct(cartProduct);
		
	}

	public void deleteCartProduct(CartProduct cartProduct) {
		cartProductDAO.deleteCartProduct(cartProduct);
		
	}

}
