package com.uol.desafio.productTeste;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.uol.desafio.entity.Product;
import com.uol.desafio.repository.ProductRepository;
import com.uol.desafio.specification.ProductSpecification;
import com.uol.desafio.specification.SearchCriteria;
import com.uol.desafio.specification.SearchOperation;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class ProductRepositoryTest {
	
	@Autowired
	ProductRepository prodRepository;
	
	@Test
	public void searchAProduct() {
		
		Product product1 = new Product("Prod t", "Descricao1", 555.98);
		Product product2 = new Product("Teste", "Descricao2", 313.11);
		Product product3 = new Product("Prod t", "Descricao1", 405.30);
		
		List<Product> list = Arrays.asList(product1,product2,product3 );
		list = prodRepository.saveAll(list);
		
		ProductSpecification spec = new ProductSpecification();
		
		 spec.add(new SearchCriteria("price", SearchOperation.GREATER_THAN_EQUAL, 405.20));
		 
		 spec.add(new SearchCriteria("price", SearchOperation.LESS_THAN_EQUAL, 559.10));
		
		List<Product> productList = prodRepository.findAll(spec);
		
		assertEquals(2, productList.size());
		assertThat(productList.get(0).getPrice() == 555.98);
		assertThat(productList.get(1).getPrice() == 405.30);	
		
	}
	
	@Test
	public void saveAproduct() {
		Product product = new Product("Teste1", "Desc1", 5.38);
		Product prodSaved = this.prodRepository.save(product);
		
		assertThat(prodSaved).isNotNull();
		assertThat(prodSaved.getName()).isEqualTo("Teste1");
		assertThat(prodSaved.getDescription()).isEqualTo("Desc1");
		assertThat(prodSaved.getPrice()).isEqualTo(5.38);
	}
	
	@Test
	public void editAProduct() {
		Product product = new Product("Teste1", "Desc1", 5.38);
		Product prodSaved = this.prodRepository.save(product);
		
		Product prodEdit = prodSaved;
		
		prodEdit.setName("Edited");
		prodEdit.setDescription("EditedDescription");
		prodEdit.setPrice(9.57);
		
		Product prodAfeterEdited = this.prodRepository.save(prodEdit);
		
		assertThat(prodAfeterEdited).isNotNull();
		assertEquals(prodAfeterEdited, prodEdit);
		
	}
	
	@Test
	public void deleteAProduct() {
		Product product = new Product("Teste1", "Desc1", 5.38);
		
		Product prodSaved = this.prodRepository.save(product);
		
		this.prodRepository.deleteById(prodSaved.getId());
		
		Optional<Product> prodAfeterDelete = prodRepository.findById(prodSaved.getId());
		
		assertTrue(prodAfeterDelete.isEmpty());
	}
}
