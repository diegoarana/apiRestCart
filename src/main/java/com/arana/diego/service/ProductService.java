package com.arana.diego.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.arana.diego.data.IProductDAO;
import com.arana.diego.model.Product;

public class ProductService implements IProductService{
	
	@Autowired
	IProductDAO productDAO;

	public Product getProduct(Long idProduct) {
		return productDAO.getProduct(idProduct);
	}

	public List<Product> listProducts() {
		return productDAO.listProducts();
	}

}
