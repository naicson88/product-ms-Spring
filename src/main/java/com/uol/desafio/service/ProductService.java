package com.uol.desafio.service;

import java.util.List;

import com.uol.desafio.entity.Product;

public interface ProductService {
	
	public Product saveProduct(Product product);
	
	public Product editProduct(Product product, long producId);
	
	public Product searchProducById(long productId);
	
	public List<Product> searchAllProducts();
	
	public List<Product> searchWithFilters(Double min_price, Double max_price, String q);
	
	public void deleteProduct(Long producId);
}
