package com.arana.diego.data;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.arana.diego.model.Product;

public class ProductDAO implements IProductDAO{
	
	@Autowired
	SessionFactory sessionFactory; 

	public Product getProduct(Long idProduct) {
		Session session = sessionFactory.openSession();
		Product product = session.load(Product.class, idProduct);
		session.close();
		return product;
	}

	public List<Product> listProducts() {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		List<Product> listProduct = session.createQuery("from Product").list();
		session.close();
		return listProduct;
	}

}
