package org.example.market.repository;

import org.example.market.domain.Member;
import org.example.market.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByStatus(String status);
    List<Product> findBySeller(Member seller);
    List<Product> findByBuyer(Member buyer);
}
