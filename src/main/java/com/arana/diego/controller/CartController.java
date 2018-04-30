package com.arana.diego.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.arana.diego.model.Cart;
import com.arana.diego.model.SimpleCart;
import com.arana.diego.model.SpecialDateCart;
import com.arana.diego.model.User;
import com.arana.diego.model.VipCart;
import com.arana.diego.service.IUserService;

@CrossOrigin(origins ="*")
@RestController
public class CartController {
	
	@Autowired
	IUserService userService;
	
	private Boolean specialDate = false;
	
	@RequestMapping(value="create/", method = RequestMethod.POST)
	public ResponseEntity<Cart> createCart(@RequestBody User user){
		
		User userLogged = userService.getUser(user.getDni());
		Cart cart;
		
		
		if (userLogged == null) {
			cart = new SimpleCart();
			return new ResponseEntity<Cart>(cart, HttpStatus.BAD_REQUEST);
			
		}
			if (userLogged.getVip()){
				cart = new VipCart();
				cart.setId(1L);
			}
			else if (specialDate){
				cart = new SpecialDateCart();
				cart.setId(1L);
			}
			else {
				cart = new SimpleCart();
				cart.setId(1L);
			}
		return new ResponseEntity<Cart>(cart, HttpStatus.CREATED);
	}

}
