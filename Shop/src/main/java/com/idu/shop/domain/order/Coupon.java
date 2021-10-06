package com.idu.shop.domain.order;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Coupon {
    private int id;
    private String name;
    private int price;
    private int percent;
}
