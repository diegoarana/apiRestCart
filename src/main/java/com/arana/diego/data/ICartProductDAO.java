package com.arana.diego.data;

import java.util.List;

import com.arana.diego.model.CartProduct;

public interface ICartProductDAO {
	
	public List<CartProduct> getProducts(Long CartId);
	
	public void addCartProduct(CartProduct cartProduct);
	
	public void updateCartProduct(CartProduct cartProduct);

}
