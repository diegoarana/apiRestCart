package com.arana.diego.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.arana.diego.data.ICartDAO;
import com.arana.diego.model.Cart;

public class CartService implements ICartService{
	
	@Autowired
	ICartDAO cartDAO;

	public void createCart(Cart newCart) {
		cartDAO.createCart(newCart);
		
	}

	public Cart getCart(Long cartId) {
		return cartDAO.getCart(cartId);
	}

}
