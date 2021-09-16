package com.uol.desafio.productTeste;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.uol.desafio.entity.Product;
import com.uol.desafio.repository.ProductRepository;
import com.uol.desafio.service.ProductService;
import com.uol.desafio.specification.ProductSpecification;

/*@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
x*/
@SpringBootTest
@AutoConfigureMockMvc
public class ProductServiceTest {
	

	ProductService prodService;
	
	@MockBean
	ProductRepository repository;
	
	@BeforeEach
	public void setUp() {
		this.prodService = new ProductService();
		
	}
	
	
	@Test
	public void saveAProduct() {
		Product product = new Product("Teste1", "Descricao1", 9.99);
		product.setId(1L);
		Mockito.when(repository.save(product)).thenReturn(product);
		
		Product prodReturned = prodService.saveProduct(product);
		
		assertThat(prodReturned.getId()).isNotNull();
		assertThat(prodReturned.getName()).isEqualTo(product.getName());
		assertThat(prodReturned.getDescription()).isEqualTo(product.getDescription());
		assertThat(prodReturned.getPrice()).isEqualTo(product.getPrice());			
	}
	
	@Test
	public void dontLetSaveAProductIncomplete() {
		Product product = new Product(" ", "Descricao1", 9.99);	
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> prodService.saveProduct(product));
		
	}
	
	@Test
	public void editAProduct() {
		Product product = new Product("BeforeEdit", "DescricaoBeforeEdit", 0.99);
		product.setId(1L);
		Product editedProduct = new Product("After Edit", "After Description", 5.38);
		editedProduct.setId(1L);
		
		Mockito.when(repository.save(product)).thenReturn(editedProduct);
		
		Product prodAfterEdition = prodService.editProduct(product, 1L);
		
		assertThat(editedProduct.getId()).isEqualTo(prodAfterEdition.getId());
		assertEquals("After Edit", prodAfterEdition.getName());
		assertEquals("After Description", prodAfterEdition.getDescription());
		assertEquals(5.38, prodAfterEdition.getPrice());
		
	}
	
	@Test
	public void editProductThatDontExist() {
		Product product = new Product("BeforeEdit", "DescricaoBeforeEdit", 0.99);
		product.setId(-1L);
	
		Assertions.assertThrows(NoSuchElementException.class, () -> prodService.editProduct(product, product.getId()));
	}
	
	@Test
	public void getProductByTheId() {
		Product product = new Product("Teste3", "Descricao3", 41.51);
		product.setId(1L);
		
		Mockito.when(repository.findById(product.getId())).thenReturn(Optional.of(product));

		Product productFound = prodService.searchProducById(product.getId());
		
		assertNotNull(productFound);
		assertThat(productFound.getId()).isEqualTo(product.getId());
		assertEquals("Teste3", productFound.getName());
		assertEquals("Descricao3", productFound.getDescription());	
	}
	
	@Test
	public void searchProductByIdThatDontExist() {
		Product product = new Product("BeforeEdit", "DescricaoBeforeEdit", 0.99);
		product.setId(-1L);	
		Assertions.assertThrows(NoSuchElementException.class, () -> prodService.searchProducById(product.getId()));
	}
	
	@Test
	public void listAllProducts() {
		Product product1 = new Product("List1", "Descricao1", 5.98);
		Product product2 = new Product("List2", "Descricao2", 33.11);
		
		Mockito.when(repository.findAll()).thenReturn(Arrays.asList(product1, product2));
		
		List<Product> productList = prodService.searchAllProducts();
		
		assertNotNull(productList);
		assertEquals(2, productList.size());
		assertEquals("List1", productList.get(0).getName());
		assertEquals("List2", productList.get(1).getName());
		
	}
	
	@Test
	public void returnEmptyArrayIfThereIsNoProduct() {
		
		Mockito.when(repository.findAll()).thenReturn(Collections.emptyList());
		
		List<Product> productList = prodService.searchAllProducts();
		
		assertThat(productList.isEmpty());
		assertNotNull(productList);
		assertThat(productList.size() == 0);
	}
	
	
	@Test
	public void searchProductWithFilter() {
		
		Product product1 = new Product("List1", "Descricao1", 5.98);
		Product product2 = new Product("List2", "Descricao2", 33.11);
		Product product3 = new Product("List3", "Descricao3", 4.30);
		ProductSpecification spec = new ProductSpecification();
		
		Mockito.when(repository.findAll(spec)).thenReturn(Arrays.asList(product1, product2));
		
		List<Product> productList = prodService.searchWithFilters(5.98, 40.12, "List");
		
		assertEquals(2, productList.size());
		assertThat(productList.get(0).getPrice() > 5.97);
		assertThat(productList.get(1).getPrice() < 40.13);	
		assertThat(product3).isNotIn(productList);		
	}
	
	@Test
	public void errorSearchProductWithAllParametersInvalid() {
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> prodService.searchWithFilters(null, null, " "));
	}
	
	@Test
	public void returnEmptyListInSearch() {
		Product product1 = new Product("List1", "Descricao1", 5.98);
		Product product3 = new Product("List3", "Descricao3", 4.30);
		
		ProductSpecification spec = new ProductSpecification();
		
		Mockito.when(repository.findAll(spec)).thenReturn(Collections.emptyList());
		
		List<Product> productList = prodService.searchWithFilters(39.10, null, "Listtt");
		
		assertEquals(0, productList.size());
		assertThat(productList.isEmpty());
		assertThat(product1).isNotIn(productList);	
		assertThat(product3).isNotIn(productList);		
	}
	
	@Test
	public void deleteAProductById() {
		Product product = new Product("Teste3", "Descricao3", 41.51);
		Product prodSaved = repository.save(product);
		
		prodService.deleteProduct(prodSaved.getId());
		
		boolean prodDeleted = repository.existsById(prodSaved.getId());
		
		assertThat(prodDeleted).isFalse();
		
	}
	
}
