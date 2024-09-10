package org.example.market.controller;

import lombok.RequiredArgsConstructor;
import org.example.market.domain.Member;
import org.example.market.domain.Product;
import org.example.market.domain.dto.BuyProductRequest;
import org.example.market.domain.dto.ProductDetailResponse;
import org.example.market.domain.dto.ProductRegisterRequest;
import org.example.market.exception.ProductNotFoundException;
import org.example.market.exception.UnauthorizedException;
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
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final MemberService memberService;


    @PostMapping("/add") // 제품 등록
    public ResponseEntity<?> addProduct(@RequestBody ProductRegisterRequest productRegisterRequest, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        Member member = memberService.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));
        productRegisterRequest.setSeller(member);
        productRegisterRequest.setStatus(Product.ProductStatus.FOR_SALE);
        return ResponseEntity.ok(productService.save(productRegisterRequest.toEntity()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailResponse> getProductById(@PathVariable Long id) {
        return productService.findById(id)
                .map(product -> ResponseEntity.ok(new ProductDetailResponse(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/buy")
    public ResponseEntity<?> buyProduct(@PathVariable Long id, @RequestBody BuyProductRequest buyProductRequest, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        Product product = productService.findById(id).orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));
        Member buyer = memberService.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));
        productService.buyProduct(id,buyer,buyProductRequest.getPrice());
        return ResponseEntity.status(HttpStatus.CONFLICT).body("구매할 수 없는 제품입니다.");
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<?> approveSale(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        Member seller = memberService.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));
        Product product = productService.approveSale(id,seller);

        return ResponseEntity.ok(product);
    }
}
