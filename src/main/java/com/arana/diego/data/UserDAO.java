package com.arana.diego.data;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.arana.diego.model.User;

public class UserDAO implements IUserDAO{
	
	@Autowired
	SessionFactory sessionFactory; 

	@SuppressWarnings("deprecation")
	public User getUserByDni(int dniUser) {
		Session session = sessionFactory.openSession();
		User user = (User) session.createCriteria(User.class)
			.add(Restrictions.eq("dni", dniUser)) 
			.uniqueResult();
		session.close();
		return user;
	}
	
	

}
