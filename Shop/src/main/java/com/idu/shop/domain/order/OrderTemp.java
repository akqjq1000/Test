package com.idu.shop.domain.order;

import com.idu.shop.domain.product.Product;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class OrderTemp {
    private int id;
    private List<OrderOption> orderOptions;
    private Product product;
    private int memberNo;
    private int count;
    private int discountPrice;
    private int totalPrice;
    private int couponNo;
}
