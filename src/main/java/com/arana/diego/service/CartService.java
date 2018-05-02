package com.arana.diego.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.arana.diego.data.ICartDAO;
import com.arana.diego.model.Cart;

public class CartService implements ICartService{
	
	@Autowired
	ICartDAO cartDAO;

	public Cart createCart(Cart newCart) {
		return cartDAO.createCart(newCart);
		
	}
	
	public Cart getCart(Long cartId) {
		return cartDAO.getCart(cartId);
	}

	public void deleteCart(Cart cart) {
		cartDAO.deleteCart(cart);
		
	}

}
