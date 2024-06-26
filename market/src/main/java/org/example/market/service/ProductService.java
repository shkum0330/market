package org.example.market.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.market.domain.Member;
import org.example.market.domain.Product;
import org.example.market.domain.dto.ProductRegisterRequest;
import org.example.market.exception.ProductNotFoundException;
import org.example.market.exception.UnauthorizedException;
import org.example.market.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product save(Product product) {
        log.info("제품 등록 = {}",product);
        return productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> findByStatus(Product.ProductStatus status) {
        return productRepository.findByStatus(status);
    }

    public List<Product> findBySeller(Member seller) {
        return productRepository.findBySeller(seller);
    }

    public List<Product> findByBuyer(Member buyer) {
        return productRepository.findByBuyer(buyer);
    }

    public void buyProduct(Long productId, Member buyer, double offeredPrice) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("존재하지 않는 제품입니다."));

        if (product.getSeller().equals(buyer)) {
            throw new UnauthorizedException("판매자가 본인의 제품을 구매할 수 없습니다.");
        }

        if (product.getStatus() != Product.ProductStatus.FOR_SALE) {
            throw new IllegalStateException("구매할 수 없는 상태입니다.");
        }

        if (product.getPrice() != offeredPrice) {
            throw new IllegalArgumentException("제시한 가격이 일치하지 않습니다.");
        }

        product.setReserved(buyer);

    }

    public Product approveSale(Long productId, Member seller) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));

        if (!product.getSeller().equals(seller)) {
            throw new UnauthorizedException("판매자가 아닙니다.");
        }

        if (product.getStatus() != Product.ProductStatus.RESERVED) {
            throw new IllegalStateException("판매를 승인할 수 없는 상태입니다.");
        }

        product.saleApproved();
        return productRepository.save(product);
    }
}
