package com.arana.diego.service;

import com.arana.diego.model.Cart;

public interface ICartService {
	
	public void createCart(Cart newCart);
	
	public Cart getCart(Long cartId);

}
