package org.example.market.controller;

import lombok.RequiredArgsConstructor;
import org.example.market.domain.Member;
import org.example.market.domain.Product;
import org.example.market.domain.dto.ProductRegisterRequest;
import org.example.market.exception.ProductNotFoundException;
import org.example.market.service.MemberService;
import org.example.market.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final MemberService memberService;


    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody ProductRegisterRequest productRegisterRequest, @AuthenticationPrincipal UserDetails userDetails) {
        Member member = memberService.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));
        productRegisterRequest.setSeller(member);
        productRegisterRequest.setStatus(Product.ProductStatus.FOR_SALE);
        return ResponseEntity.ok(productService.save(productRegisterRequest.toEntity()));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/buy")
    public ResponseEntity<?> buyProduct(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Product product = productService.findById(id).orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));
        Member buyer = memberService.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));
        if ("판매중".equals(product.getStatus().getDescription())) {
;
            product.setReserved(buyer, Product.ProductStatus.RESERVED);
            return ResponseEntity.ok(productService.save(product));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("구매할 수 없는 제품입니다.");
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<?> approveSale(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Product product = productService.findById(id).orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));
        Member seller = memberService.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));
        return ResponseEntity.status(HttpStatus.CONFLICT).body("판매를 승인할 수 없습니다.");
    }
}
