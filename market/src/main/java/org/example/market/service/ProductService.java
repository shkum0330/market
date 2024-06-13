package org.example.market.service;

import lombok.RequiredArgsConstructor;
import org.example.market.domain.Member;
import org.example.market.domain.Product;
import org.example.market.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private ProductRepository productRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> findByStatus(String status) {
        return productRepository.findByStatus(status);
    }

    public List<Product> findBySeller(Member seller) {
        return productRepository.findBySeller(seller);
    }

    public List<Product> findByBuyer(Member buyer) {
        return productRepository.findByBuyer(buyer);
    }
}
