package org.example.market.service;

import org.example.market.domain.Member;
import org.example.market.domain.Product;
import org.example.market.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    private Product product1,product2;
    private Member seller;
    private Member buyer;

    @BeforeEach
    void setUp() {
        seller = new Member(1L,"seller","1234","ROLE_USER");
        buyer = new Member(2L,"buyer","1234","ROLE_USER");

        product1 = new Product(1L,"Test Product1",10000L, Product.ProductStatus.FOR_SALE,seller,null);
        product1 = new Product(1L,"Test Product2",20000L, Product.ProductStatus.RESERVED,seller,buyer);
    }
    @Test
    void addProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product1);

        Product savedProduct = productService.save(product1);

        assertNotNull(savedProduct);
        assertEquals(product1.getName(), savedProduct.getName());
        verify(productRepository, times(1)).save(product1);
    }

    @Test
    void findAllProducts() {
        List<Product> products = List.of(product1);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> foundProducts = productService.findAll();

        assertNotNull(foundProducts);
        assertEquals(1, foundProducts.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void findProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));

        Optional<Product> foundProduct = productService.findById(1L);

        assertTrue(foundProduct.isPresent());
        assertEquals(product1.getName(), foundProduct.get().getName());
        verify(productRepository, times(1)).findById(1L);
    }
    @Test
    void testFindProductsByStatus() {
        List<Product> products = List.of(product1);
        when(productRepository.findByStatus(Product.ProductStatus.FOR_SALE)).thenReturn(products);

        List<Product> foundProducts = productService.findByStatus(Product.ProductStatus.FOR_SALE);

        assertNotNull(foundProducts);
        assertEquals(1, foundProducts.size());
        verify(productRepository, times(1)).findByStatus(Product.ProductStatus.FOR_SALE);
    }

    @Test
    void testFindProductsBySeller() {
        List<Product> products = List.of(product1);
        when(productRepository.findBySeller(seller)).thenReturn(products);

        List<Product> foundProducts = productService.findBySeller(seller);

        assertNotNull(foundProducts);
        assertEquals(1, foundProducts.size());
        verify(productRepository, times(1)).findBySeller(seller);
    }

}