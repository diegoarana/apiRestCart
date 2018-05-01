package com.arana.diego.service;

import java.util.List;

import com.arana.diego.model.CartProduct;

public interface ICartProductService {
	
	public List<CartProduct> getProducts(Long cartId);
	
	public void addCartProduct(CartProduct cartProduct);
	
	public void updateCartProduct(CartProduct cartProduct);

}
