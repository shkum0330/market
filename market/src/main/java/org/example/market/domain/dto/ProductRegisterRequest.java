package org.example.market.domain.dto;

import lombok.Data;
import org.example.market.domain.Member;
import org.example.market.domain.Product;

import static org.example.market.domain.Product.ProductStatus.FOR_SALE;

@Data
public class ProductRegisterRequest {
    private String name;
    private Long price;
    private Member seller;
    private Product.ProductStatus status;

    public Product toEntity(){
        return Product.builder().name(name).price(price).seller(seller).status(status).build();
    }
}
