package org.example.market.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.mapping.Join;

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
    private String status; // "판매중", "예약중", "완료"

    @ManyToOne
    @JoinColumn(name="seller_id")
    private Member seller;

    @ManyToOne
    @JoinColumn(name="buyer_id")
    private Member buyer;
}
