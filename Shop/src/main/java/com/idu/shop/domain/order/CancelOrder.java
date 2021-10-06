package com.idu.shop.domain.order;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class CancelOrder {
    private int id;
    private int memberNo;
    private int orderNo;
    private String cancelDate;
    private int count;
    private int price;
    private int returnPrice;
}
