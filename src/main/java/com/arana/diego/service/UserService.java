package com.arana.diego.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.arana.diego.data.IUserDAO;
import com.arana.diego.model.User;

public class UserService implements IUserService{
	
	@Autowired
	IUserDAO userDAO;

	public User getUser(int userDni) {
		return userDAO.getUserByDni(userDni);
	}
	
	

}
