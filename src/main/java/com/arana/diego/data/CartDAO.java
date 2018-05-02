package com.arana.diego.data;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.arana.diego.model.Cart;

public class CartDAO implements ICartDAO{
	
	@Autowired
	SessionFactory sessionFactory; 

	@Transactional
	public Cart createCart(Cart newCart) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(newCart);
		tx.commit();
		session.close();
		return newCart;
		
	}

	
	@SuppressWarnings("deprecation")
	public Cart getCart(Long cartId) {

		Session session = sessionFactory.openSession();
		Cart cart = (Cart) session.createCriteria(Cart.class)
				.add(Restrictions.eq("id", cartId))
				.uniqueResult();
		session.close();
		return cart;
	}


	public void deleteCart(Cart cart) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(cart);
		tx.commit();
		session.close();
		
	}
	
	public void updateCart(Cart cart){
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.merge(cart);
		tx.commit();
		session.close();
	}

}
