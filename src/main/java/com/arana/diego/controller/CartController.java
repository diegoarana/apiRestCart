package com.arana.diego.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.arana.diego.model.Product;
import com.arana.diego.model.SpecialDateCart;
import com.arana.diego.model.User;
import com.arana.diego.model.VipCart;
import com.arana.diego.service.ICartProductService;
import com.arana.diego.service.ICartService;
import com.arana.diego.service.IUserService;
import com.arana.diego.service.ProductService;

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
	
//------------------------------- creando un carrito nuevo
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
	
	
//---------------------------------------- trayendo un carrito por id
	@RequestMapping(value="getCart/{cartId}", method = RequestMethod.GET)
	public ResponseEntity<Cart> getCart(@PathVariable("cartId") Long cartId){
		
		try{
			
			Cart cart = cartService.getCart(cartId);
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
			
		}catch(Exception e){
			Cart emptycart = new Cart();
			return new ResponseEntity<Cart>(emptycart, HttpStatus.BAD_REQUEST);
		}
		
	}
	
// -------------------------------------Eliminando carrito	
	@RequestMapping(value="deleteCart/{cartId}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCart(@PathVariable("cartId") Long id ){
		
		try {
			Cart cart = cartService.getCart(id);
			cart.setListProduct(cartProductService.getProducts(id));
			cartService.deleteCart(cart);
		}catch(Exception e){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	
// -----------------------------------pagando carrito
	@CrossOrigin(origins ="*")
	@RequestMapping(value="payCart/", method= RequestMethod.POST)
	public ResponseEntity<Cart> payCart(@RequestBody Cart payCart){
		
		// el pago de un carrito solamente persiste el monto total del carrito
		
		try {
			Cart cart = cartService.getCart(payCart.getId());
			cart.setListProduct(cartProductService.getProducts(payCart.getId()));
			
			Cart emptyCart = new Cart();
			
			if (cart.getUser().getVip()){
				
				VipCart vipcart = (VipCart) cart;
				vipcart.setTotalAmount(vipcart.calculateTotalPrice());
				cartService.updateCart(vipcart);
				return new ResponseEntity<Cart>(emptyCart, HttpStatus.OK);
				
			}else if(specialDate){
				
				SpecialDateCart specialCart = (SpecialDateCart) cart;
				specialCart.setTotalAmount(specialCart.calculateTotalPrice());
				cartService.updateCart(specialCart);
				return new ResponseEntity<Cart>(emptyCart, HttpStatus.OK);
				
			} else{
				
				cart.setTotalAmount(cart.calculateTotalPrice());
				cartService.updateCart(cart);
				return new ResponseEntity<Cart>(emptyCart, HttpStatus.OK);
				
			}
		}catch(Exception e){
			return new ResponseEntity<Cart>(HttpStatus.BAD_REQUEST);
		}
	
		
	}
	
	
// ---------------------------------devolver los cuatro productos mas caros
	@RequestMapping(value="getMoreExpensiveProducts/", method=RequestMethod.POST)
	public ResponseEntity<List<Product>> getMoreExpensiveProducts(@RequestBody User user){
		
		try {
			
			User userLogged = userService.getUser(user.getDni());
			
			List<Cart> cartList = userLogged.getLisCart();
			
			// elimino de la lista los carritos que tienen monto total en null
			setCartsWithTotalAmount(cartList);
			
			loadCartProducts(cartList);
			// creo un set con la lista de los porductos de todos los carritos comprados
			Set<Product> productsSet = nonRepeatedProducts(cartList);
			
			List<Product> productList = new ArrayList<Product>(productsSet);
			// ordeno la lista de producto por precio
			sortListProduct(productList);
			
			// genero la sublista que devuelve los 4 productos mas caros
			List<Product> responseList = productList.subList(0, 4); 
			
			return new ResponseEntity<List<Product>>(responseList, HttpStatus.OK);
			
		}catch(Exception e){
			List<Product> emptyList = new ArrayList<Product>();
			return new ResponseEntity<List<Product>>(emptyList, HttpStatus.BAD_REQUEST);
		}

	}
	
	
	
	
	public void loadCartProducts(List<Cart> cartList){
		for(Cart cart : cartList){
			cart.setListProduct(cartProductService.getProducts(cart.getId()));
		}
	}
	
	
	public Set<Product> nonRepeatedProducts(List<Cart> cartList){
		Set<Product> setOfProducts = new HashSet<Product>();
		for (Cart cart : cartList){
			for (CartProduct item : cart.getListProduct()){
				setOfProducts.add(item.getProduct());
			}
			
		}
		
		return setOfProducts;
	}
	
	
	public void setCartsWithTotalAmount(List<Cart> cartList){
		cartList.removeIf(new Predicate<Cart>() {
			public boolean test(Cart c) {
				return c.getTotalAmount() == null;
				}
			});
	}
	
	
	public void sortListProduct(List<Product> productList){
		Collections.sort(productList, new Comparator<Product>() {

	        public int compare(Product p1, Product p2) {
	            return p2.getPrice().compareTo(p1.getPrice());
	        }
	    });
	}
	

}
