package com.arana.diego.data;

import java.util.List;

import com.arana.diego.model.Product;

public interface IProductDAO {
	
	public Product getProduct(Long idProduct);
	
	public List<Product> listProducts();

}
