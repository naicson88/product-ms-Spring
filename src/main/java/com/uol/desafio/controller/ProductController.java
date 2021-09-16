package com.uol.desafio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.uol.desafio.entity.Product;
import com.uol.desafio.service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	ProductService prodService;
	
	
	@PostMapping("/products")
	@ResponseBody
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		
		Product prodSaved = prodService.saveProduct(product);
		
		return new ResponseEntity<Product>(prodSaved, HttpStatus.OK);
	}
	
	@PutMapping("/products/{id}")
	@ResponseBody
	public ResponseEntity<Product> editProdct(@RequestBody Product product, @PathVariable("id") Long productId) {		
		Product prodEdited = prodService.editProduct(product, productId);
		
		return new ResponseEntity<Product>(prodEdited, HttpStatus.OK);		
		
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> searchProducById(@PathVariable("id") Long productId){
		
		Product product = prodService.searchProducById(productId);
		
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}
	
	@GetMapping("/products")
	@ResponseBody
	public ResponseEntity<List<Product>> searchAllProducts(){
		List<Product> products = prodService.searchAllProducts();
		
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	
	@GetMapping("/products/search")
	@ResponseBody
	public ResponseEntity<List<Product>> searchProductsWithFilter(@RequestParam(required = false) Double min_value,
			@RequestParam(required = false) Double max_value, @RequestParam(required = false) String q){
		
		List<Product> products = prodService.searchWithFilters(min_value, max_value, q);
		
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	
	@DeleteMapping("products/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable("id") Long productId){
		
		prodService.deleteProduct(productId);
		
		return new ResponseEntity<>("Successfully deleted",HttpStatus.OK);
	}
	
	
}
