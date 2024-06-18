package org.example.market.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private Long id;
    private String name;
    private Long price;

    @Enumerated(EnumType.STRING)
    private ProductStatus status; // "판매중", "예약중", "완료"

    @Getter
    public enum ProductStatus {
        FOR_SALE("판매중"),
        RESERVED("예약중"),
        SOLD_OUT("완료");

        private final String description;

        ProductStatus(String description) {
            this.description = description;
        }

    }

    @ManyToOne
    @JoinColumn(name="seller_id")
    private Member seller;

    @ManyToOne
    @JoinColumn(name="buyer_id")
    private Member buyer;

    public Product(Long id, String name, Long price, ProductStatus status, Member seller, Member buyer) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
        this.seller = seller;
        this.buyer = buyer;
    }

    @Builder
    public Product(String name, Long price, ProductStatus status, Member seller) {
        this.name = name;
        this.price = price;
        this.status = status;
        this.seller = seller;
    }

    public void setReserved(Member buyer,ProductStatus status){
        this.buyer=buyer;
        this.status=status;
    }
}
