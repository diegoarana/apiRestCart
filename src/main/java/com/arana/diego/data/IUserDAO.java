package com.arana.diego.data;

import com.arana.diego.model.User;

public interface IUserDAO {
	
	public User getUserByDni(int dniUser);

}
