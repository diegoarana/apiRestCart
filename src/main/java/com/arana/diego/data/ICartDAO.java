package com.arana.diego.data;

import com.arana.diego.model.Cart;

public interface ICartDAO {
	
	public Cart createCart(Cart newCart);
	
	public Cart getCart(Long cartId);

}
