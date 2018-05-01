package com.arana.diego.service;

import java.util.List;

import com.arana.diego.model.Product;

public interface IProductService {
	
	public Product getProduct(Long idProduct);
	
	public List<Product> listProducts();

}
