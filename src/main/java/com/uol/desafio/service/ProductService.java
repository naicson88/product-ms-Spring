package com.uol.desafio.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uol.desafio.entity.Product;
import com.uol.desafio.repository.ProductRepository;
import com.uol.desafio.specification.ProductSpecification;
import com.uol.desafio.specification.SearchCriteria;
import com.uol.desafio.specification.SearchOperation;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository prodRepository;
	
	Product prod;
	
	public Product saveProduct(Product product)  {	
			if(product == null)
				throw new IllegalArgumentException("Product not informed, can't be saved.");
			
			 prod = prodRepository.save(product);
			 
			 return prod;		
		}
		
	public Product editProduct(Product product, long producId) {
		
		Optional<Product> prodSearched = prodRepository.findById(producId);
		
		if(prodSearched.isEmpty())
			throw new NoSuchElementException("Product not found.");
		
		prod = prodRepository.save(product);
		
		return prod;		
		
	} 
	
	public Product searchProducById(long productId) {
		
		if(productId <= 0)
			throw new IllegalArgumentException("Invalid product ID");
		
		Optional<Product> prodSearched = prodRepository.findById(productId);
		
		if(prodSearched.isEmpty())
			throw new NoSuchElementException("Product not found.");
		
		return prodSearched.get();
	}
	
	public List<Product> searchAllProducts() {
		
		List<Product> allProducts = prodRepository.findAll();
		
		return allProducts;
	}
	
	public List<Product> searchWithFilters(double min_price, double max_price, String q) {
		
		if(min_price == 0.0 && max_price == 0.0 && "".equals(q))
			throw new IllegalArgumentException("No parameter informed for search");
		
		ProductSpecification spec = new ProductSpecification();
		
		if(min_price > 0)
			spec.add(new SearchCriteria("price", SearchOperation.GREATER_THAN_EQUAL, min_price));
		
		if(max_price > 0)
			spec.add(new SearchCriteria("price", SearchOperation.LESS_THAN_EQUAL, max_price));
		
		if(!" ".equals(q) && q != null)
			spec.add(new SearchCriteria("nameOrDescription", SearchOperation.MATCH, q));
		
		List<Product> products = prodRepository.findAll(spec);
		
		if(products == null || products.size() == 0)
			 throw new NoSuchElementException("No product found with for parameters.");
		
		return products;
	}
}
