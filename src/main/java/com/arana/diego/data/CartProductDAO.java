package com.arana.diego.data;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.arana.diego.model.CartProduct;

public class CartProductDAO implements ICartProductDAO{
	
	@Autowired
	SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<CartProduct> getProducts(Long cartId) {
		Session session = sessionFactory.openSession();
		Query q = session.createQuery("from CartProduct where idCart = :id");
		q.setParameter("id", cartId);
		List<CartProduct> resultList = q.getResultList();
		session.close();
		return resultList;
	}

	public void addCartProduct(CartProduct cartProduct) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(cartProduct);
		tx.commit();
		session.close();
		
	}

	public void updateCartProduct(CartProduct cartProduct) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.merge(cartProduct);
		tx.commit();
		session.close();
		
	}

}
