package com.uol.desafio;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.uol.desafio.entity.Product;
import com.uol.desafio.repository.ProductRepository;

@SpringBootApplication
public class DesafioApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DesafioApplication.class, args);
	}
	
	@Autowired
	private ProductRepository prodRepository;
	
	@Override
	public void run(String...args) throws Exception {
		
		Product prod1 = new Product("4 teste", "description 1", 3.5);
		Product prod2 = new Product("prod1 teste", "description 2 teste", 10.8);
		Product prod3 = new Product("prod3 teste", " 3 teste", 2.99);
		Product prod4 = new Product("prod4 teste", "4 teste", 0.87);
		
		ArrayList<Product> prods = new ArrayList<>();
		
		Arrays.asList(prod1,prod2,prod3,prod4).stream().forEach(p -> {

				prods.add(p);
		});
		
		
		prodRepository.saveAll(prods);
	}

}
