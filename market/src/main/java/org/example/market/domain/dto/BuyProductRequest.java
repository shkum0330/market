package org.example.market.domain.dto;

import lombok.Data;

@Data
public class BuyProductRequest {
    private Long price;
    private Long quantity;
}
