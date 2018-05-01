package com.arana.diego.controller;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.arana.diego.model.Cart;
import com.arana.diego.model.CartProduct;
import com.arana.diego.model.SpecialDateCart;
import com.arana.diego.model.User;
import com.arana.diego.model.VipCart;
import com.arana.diego.service.ICartProductService;
import com.arana.diego.service.ICartService;
import com.arana.diego.service.IUserService;

@CrossOrigin(origins ="*")
@RestController
public class CartController {
	
	@Autowired
	IUserService userService;
	@Autowired
	ICartService cartService;
	@Autowired
	ICartProductService cartProductService;
	
	private Boolean specialDate = false;
	
	// creando un carrito nuevo
	@RequestMapping(value="create/", method = RequestMethod.POST)
	public ResponseEntity<Cart> createCart(@RequestBody User user){
		
		User userLogged = userService.getUser(user.getDni());
		Cart newCart;
		
		
		if (userLogged == null) {
			newCart = new Cart();
			return new ResponseEntity<Cart>(newCart, HttpStatus.BAD_REQUEST);
			
		}
			if (userLogged.getVip()){
				newCart = new VipCart();
				newCart.setUser(userLogged);
				newCart = cartService.createCart(newCart);
			}
			else if (specialDate){
				newCart = new SpecialDateCart();
				newCart.setUser(userLogged);
				newCart = cartService.createCart(newCart);
			}
			else {
				newCart = new Cart();
				newCart.setUser(userLogged);
				newCart = cartService.createCart(newCart);
			}
		return new ResponseEntity<Cart>(newCart, HttpStatus.CREATED);
	}
	
	// trayendo un carrito por id
	@RequestMapping(value="getCart/{cartId}", method = RequestMethod.GET)
	public ResponseEntity<Cart> getCart(@PathVariable("cartId") Long cartId){
		
		Cart cart = cartService.getCart(cartId);
		Hibernate.initialize(cart.getListProduct());
		cart.setListProduct(cartProductService.getProducts(cartId));
		
		if (cart.getUser().getVip()){
			
			VipCart vipcart = (VipCart) cart;
			vipcart.setTotalAmount(vipcart.calculateTotalPrice());
			return new ResponseEntity<Cart>(vipcart, HttpStatus.OK);
			
		}else if(specialDate){
			
			SpecialDateCart specialCart = (SpecialDateCart) cart;
			specialCart.setTotalAmount(specialCart.calculateTotalPrice());
			return new ResponseEntity<Cart>(specialCart, HttpStatus.OK);
			
		} else{
			
			cart.setTotalAmount(cart.calculateTotalPrice());
			return new ResponseEntity<Cart>(cart, HttpStatus.OK);
			
		}
		
	}

}
