package com.arana.diego.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.arana.diego.model.Cart;
import com.arana.diego.model.CartProduct;
import com.arana.diego.model.Product;
import com.arana.diego.model.SpecialDateCart;
import com.arana.diego.model.VipCart;
import com.arana.diego.service.ICartProductService;
import com.arana.diego.service.ICartService;
import com.arana.diego.service.IProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
public class CartProductController {
	
	private Boolean specialDate = false;
	
	@Autowired
	ICartProductService cartProductService;
	
	@Autowired
	ICartService cartService;
	
	@Autowired
	IProductService productService;
	
	@RequestMapping(value="addCartProduct/", method=RequestMethod.POST)
	public ResponseEntity<Cart> addCartProduct(@RequestBody String body) throws JsonProcessingException, IOException{
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode bodyJson = mapper.readTree(body);
		
		Cart cart = cartService.getCart(bodyJson.get("cartId").asLong());
		cart.setListProduct(cartProductService.getProducts(cart.getId()));
		Product newProduct = productService.getProduct(bodyJson.get("productId").asLong());
		int quantity = bodyJson.get("quantity").asInt();
		
		Boolean existCartProduct = false;
		
		for (CartProduct prodInCart : cart.getListProduct()){
			// Si ya existe el producto en el carrito aumento la cantidad
			if(prodInCart.getProduct().getId() == newProduct.getId()){
				prodInCart.setQuantity(prodInCart.getQuantity() + quantity);
				cartProductService.updateCartProduct(prodInCart);
				existCartProduct = true;
				break;
			}
		}
		
		// Actualizo el precio total del carrito correspondiente
		if (cart.getUser().getVip()){
			VipCart updatedCart = (VipCart) cart;
			
			if (existCartProduct){
				updatedCart.setTotalAmount(updatedCart.calculateTotalPrice());
				return new ResponseEntity<Cart>(updatedCart, HttpStatus.OK);
			}else{
				CartProduct cartProduct = new CartProduct(quantity, cart, newProduct);
				cartProductService.addCartProduct(cartProduct);
				updatedCart.setListProduct(cartProductService.getProducts(updatedCart.getId()));
				updatedCart.setTotalAmount(updatedCart.calculateTotalPrice());
				return new ResponseEntity<Cart>(updatedCart, HttpStatus.OK);
			}
			
		}else if(specialDate){
			SpecialDateCart updatedCart = (SpecialDateCart) cart;
			
			if (existCartProduct){
				updatedCart.setTotalAmount(updatedCart.calculateTotalPrice());
				return new ResponseEntity<Cart>(updatedCart, HttpStatus.OK);
			}else{
				CartProduct cartProduct = new CartProduct(quantity, cart, newProduct);
				cartProductService.addCartProduct(cartProduct);
				updatedCart.setListProduct(cartProductService.getProducts(updatedCart.getId()));
				updatedCart.setTotalAmount(updatedCart.calculateTotalPrice());
				return new ResponseEntity<Cart>(updatedCart, HttpStatus.OK);
			}
		}else{
			Cart updatedCart = cart;
			
			if (existCartProduct){
				updatedCart.setTotalAmount(updatedCart.calculateTotalPrice());
				return new ResponseEntity<Cart>(updatedCart, HttpStatus.OK);
			}else{
				CartProduct cartProduct = new CartProduct(quantity, cart, newProduct);
				cartProductService.addCartProduct(cartProduct);
				updatedCart.setListProduct(cartProductService.getProducts(updatedCart.getId()));
				updatedCart.setTotalAmount(updatedCart.calculateTotalPrice());
				return new ResponseEntity<Cart>(updatedCart, HttpStatus.OK);
			}
		}
		
	}

}
