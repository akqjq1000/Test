package com.idu.shop.domain.order;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class ShoppingCart {
    private int id;
    private String serialNumber;
    private int cnt;
    private int memberNo;
    private int productNo;
    private int count;
    private String regdate;
}
