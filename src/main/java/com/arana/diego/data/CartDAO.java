package com.arana.diego.data;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.arana.diego.model.Cart;

public class CartDAO implements ICartDAO{
	
	@Autowired
	SessionFactory sessionFactory; 

	@Transactional
	public void createCart(Cart newCart) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(newCart);
		tx.commit();
		
	}

	public Cart getCart(Long cartId) {

		Session session = sessionFactory.openSession();
		Cart cart = session.load(Cart.class, cartId);
		session.close();
		return cart;
	}

}
