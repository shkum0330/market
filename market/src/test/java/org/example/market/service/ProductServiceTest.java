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

import java.util.List;

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

    private Product product;
    private Member seller;
    private Member buyer;

    @BeforeEach
    void setUp() {
        seller = new Member(1L,"seller","1234","ROLE_USER");
        buyer = new Member(2L,"buyer","1234","ROLE_USER");

        product = new Product(1L,"Test Product",10000L, Product.ProductStatus.FOR_SALE,seller,null);

    }
    @Test
    void testSaveProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product savedProduct = productService.save(product);

        assertNotNull(savedProduct);
        assertEquals(product.getName(), savedProduct.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testFindAllProducts() {
        List<Product> products = List.of(product);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> foundProducts = productService.findAll();

        assertNotNull(foundProducts);
        assertEquals(1, foundProducts.size());
        verify(productRepository, times(1)).findAll();
    }
}