package com.arana.diego.service;

import com.arana.diego.model.Cart;

public interface ICartService {
	
	public Cart createCart(Cart newCart);
	
	public Cart getCart(Long cartId);
	
	public void deleteCart(Cart cart);
	
	public void updateCart(Cart cart);

}
