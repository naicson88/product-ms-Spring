package com.uol.desafio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesafioApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DesafioApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/*@Autowired
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
	}*/

}
