package com.uol.desafio.service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.uol.desafio.entity.Product;
import com.uol.desafio.repository.ProductRepository;
import com.uol.desafio.specification.ProductSpecification;
import com.uol.desafio.specification.SearchCriteria;
import com.uol.desafio.specification.SearchOperation;

@Service
public class ProductServiceImpl implements ProductService {
	
	Product prod;
	
	
	ProductRepository prodRepository;
	
	public ProductServiceImpl(ProductRepository repository) {
		this.prodRepository = repository;
	}
	

	@Override
	public Product saveProduct(Product product)  {	
			if(product == null)
				throw new IllegalArgumentException("Product not informed, can't be saved.");
			
			if(product.getName() == null || " ".equals(product.getName()))
					throw new IllegalArgumentException("Product name not informed.");
			
			if(product.getDescription() == null || " ".equals(product.getDescription()))
				throw new IllegalArgumentException("Product description not informed.");
			
			 prod = prodRepository.save(product);
			 
			 return prod;		
		}
	
	@Transactional
	@Override
	public Product editProduct(Product product, long producId) {
		
		Optional<Product> prodSearched = prodRepository.findById(producId);
		
		if(prodSearched.isEmpty())
			throw new NoSuchElementException("Product not found.");
		
			Product _prod = prodSearched.get();
			
			_prod.setName(product.getName());
			_prod.setDescription(product.getDescription());
			_prod.setPrice(product.getPrice());
			
		prod = prodRepository.save(_prod);
		
		return prod;		
		
	} 
	 @Override
	public Product searchProducById(long productId) {
		
		if(productId == 0)
			throw new IllegalArgumentException("Invalid product ID");
		
		Optional<Product> prodSearched = prodRepository.findById(productId);
		
		if(prodSearched.isEmpty())
			throw new NoSuchElementException("Product not found.");
		
		return prodSearched.get();
	}
	 @Override
	public List<Product> searchAllProducts() {
		
		List<Product> allProducts = prodRepository.findAll();
		
		if(allProducts == null || allProducts.size() == 0)
			return Collections.emptyList();
		
		return allProducts;
	}
	 @Override
	public List<Product> searchWithFilters(Double min_price, Double max_price, String q) {
		
		if(min_price == null && max_price == null && (" ".equals(q) || q == null))
			throw new IllegalArgumentException("No parameter informed for search");
		
		ProductSpecification spec = new ProductSpecification();
		
		if(min_price != null && min_price > 0)
			spec.add(new SearchCriteria("price", SearchOperation.GREATER_THAN_EQUAL, min_price));
		
		if(max_price != null  &&  max_price > 0)
			spec.add(new SearchCriteria("price", SearchOperation.LESS_THAN_EQUAL, max_price));
		
		if(!" ".equals(q) && q != null)
			spec.add(new SearchCriteria("name", SearchOperation.MATCH, q));
		
		List<Product> products = prodRepository.findAll(spec);
		
		if(products == null || products.size() == 0)
			return Collections.emptyList();
		
		return products;
	}
	
	@Transactional
	 @Override
	public void deleteProduct(Long producId) {

		if(" ".equals(producId) || producId == null)
			throw new IllegalArgumentException("Product Id not informed");
		
		boolean producExist = prodRepository.existsById(producId);
		
		if(producExist)
			prodRepository.deleteById(producId);
		else
			throw new NoSuchElementException("Product not found.");
	}
	
}
